package com.aa.gsa.listener;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.Run;
import com.aa.gsa.domain.StatusMessage;
import com.aa.gsa.enums.RunStatus;
import com.aa.gsa.enums.StatusMessageType;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.MessagingProperties;
import com.aa.gsa.util.PointsProcessorConstants;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Run Execution Listener
 * @author 940914
 */
@Component
public class RunMessageProcessor implements MessageListener {
	
	private Log log = LogFactory.getLog(RunMessageProcessor.class);

	private Job job;

	private JobRepository jobRepository;
	
	@SuppressWarnings("unused")
	private CloudantClient cloudantClient;

	private Database runDB;

	private RabbitTemplate rabbitTemplate;

	private MessagingProperties msgProps;

	private BatchJobProperties batchJobProps;
		
	private SimpleJobLauncher jobLauncher;
	
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	public RunMessageProcessor(Job job, JobRepository jobRepository, 
			CloudantClient cloudantClient, 
			RabbitTemplate rabbitTemplate, 
			MessagingProperties msgProps,
			BatchJobProperties batchJobProps) {
		this.job = job;
		this.jobRepository = jobRepository;
		this.cloudantClient = cloudantClient;
		this.rabbitTemplate = rabbitTemplate;
		this.msgProps = msgProps;
		this.batchJobProps = batchJobProps;
		this.runDB = cloudantClient.database(this.batchJobProps.getRunDatabaseName(), false);
		
		/**
		 * Initialize Job Launcher
		 */
		initJobLauncher();
	}

	@Override
	public void onMessage(Message message) {
		try {
			Run run = new ObjectMapper().readValue(message.getBody(), Run.class);
			
			if (run.get_id() == null) {
		        rabbitTemplate.convertAndSend(msgProps.getLogExchange(), null, StatusMessage.textMsg("Missing run Id in "+run, StatusMessageType.ERROR));
				return; 			
			}

			/**
			 * Validate the run
			 */
			Set<String> errors = validateRun(run);
			
			if (!CollectionUtils.isEmpty(errors)) {
				failRun(run, errors);
				return; 			
			}
			
			/**
			 * Start the run
			 */
			startRun(run);

		} catch (IOException ex) {
	        rabbitTemplate.convertAndSend(msgProps.getLogExchange(), null, StatusMessage.textMsg("Error reading run document", StatusMessageType.ERROR));
			log.error("Error reading run document", ex);
		}
	}

	/**
	 * Submit the Run for processing
	 * @param run
	 */
	private void startRun(Run run) {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString(PointsProcessorConstants.SCHEDULE_FILE_ID, run.getScheduleName())
				.addString(PointsProcessorConstants.CPP_FILE_ID, run.getCppFileGroupName())
				.addString(PointsProcessorConstants.SETTINGS_DOCUMENT_NAME, run.getRulesetName())
				.addString(PointsProcessorConstants.RUN_DOCUMENT_ID, run.get_id())
				.addString(PointsProcessorConstants.RUN_NAME, run.getName())
				.addDate(PointsProcessorConstants.RUN_DATE, new Date())
				.toJobParameters();
		try {
			jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
	        rabbitTemplate.convertAndSend(msgProps.getLogExchange(), null, StatusMessage.textMsg("A run exists already with the given parameters"+run, StatusMessageType.ERROR, run));
		}
	}
	
	private Set<String> validateRun(Run run) {
		Set<String> errors = new HashSet<>(3);
		
		if (run.getCppFileGroupName() == null) {
			errors.add("CPP File Group is missing");
		}
		
		if (run.getScheduleName() == null) {
			errors.add("Schedule File Name is missing");
		}
		
		if (run.getRulesetName() == null) {
			errors.add("RulsSet is missing");
		}

		return errors;
	}
	
	private void failRun(Run run, Set<String> errors) {
		run = runDB.find(Run.class, run.get_id());
		run.setStatus(RunStatus.FAILED.getStatus());
		runDB.update(run);
		rabbitTemplate.convertAndSend(msgProps.getRunExchange(), msgProps.getStatusRoutingKey(), run);
		errors
			.parallelStream()
			.forEach(error -> rabbitTemplate.convertAndSend(msgProps.getLogExchange(), null, StatusMessage.textMsg(error, StatusMessageType.ERROR)));
	}
	
	private void initJobLauncher() {
		jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		initTaskScheduler();
	}
	
	private void initTaskScheduler() {
		taskExecutor = new ThreadPoolTaskExecutor(); 
		taskExecutor.setCorePoolSize(1);
		taskExecutor.setMaxPoolSize(1);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.initialize();
		jobLauncher.setTaskExecutor(taskExecutor);
	}
}
