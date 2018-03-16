package com.aa.gsa.config;

import static com.aa.gsa.util.PointsProcessorConstants.CPP_FILE_ID;
import static com.aa.gsa.util.PointsProcessorConstants.FROM;
import static com.aa.gsa.util.PointsProcessorConstants.GSA_RULES_PROCESSOR_JOB;
import static com.aa.gsa.util.PointsProcessorConstants.INT_EXPR;
import static com.aa.gsa.util.PointsProcessorConstants.SCHEDULE_FILE_ID;
import static com.aa.gsa.util.PointsProcessorConstants.SETTINGS_DOCUMENT_NAME;
import static com.aa.gsa.util.PointsProcessorConstants.STR_EXPR;
import static com.aa.gsa.util.PointsProcessorConstants.TO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.StationCode;
import com.aa.gsa.domain.result.CPPResult;
import com.aa.gsa.eligibility.filters.CircuityFilter;
import com.aa.gsa.eligibility.filters.EligibilityFilter;
import com.aa.gsa.eligibility.filters.GroundTimeFilter;
import com.aa.gsa.eligibility.filters.ServiceLevelFilter;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.exception.handler.PointsProcessorSkipPolicy;
import com.aa.gsa.listener.ProtocolListener;
import com.aa.gsa.listener.RunListener;
import com.aa.gsa.listener.RunMessageProcessor;
import com.aa.gsa.listener.StatusUpdateListener;
import com.aa.gsa.partitioner.CPPPartioner;
import com.aa.gsa.processor.EligibilityProcessor;
import com.aa.gsa.processor.FinalProcessor;
import com.aa.gsa.processor.InitProcessor;
import com.aa.gsa.processor.PointsProcessor;
import com.aa.gsa.processor.ScheduleFilterProcessor;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.MessagingProperties;
import com.aa.gsa.properties.SendGridProperties;
import com.aa.gsa.service.CPPReader;
import com.aa.gsa.service.CPPService;
import com.aa.gsa.service.EligibilityResultService;
import com.aa.gsa.service.EligibilityService;
import com.aa.gsa.service.EquipmentReader;
import com.aa.gsa.service.EquipmentService;
import com.aa.gsa.service.PointsService;
import com.aa.gsa.service.ResultsWriter;
import com.aa.gsa.service.ScheduleQueryService;
import com.aa.gsa.service.ScheduleService;
import com.aa.gsa.service.SettingsPerGroupService;
import com.aa.gsa.service.StationCodeReader;
import com.aa.gsa.service.impl.CPPReaderNoSQLStore;
import com.aa.gsa.service.impl.CPPServiceImpl;
import com.aa.gsa.service.impl.CloudantService;
import com.aa.gsa.service.impl.EligibilityResultServiceImpl;
import com.aa.gsa.service.impl.EligibilityServiceImpl;
import com.aa.gsa.service.impl.EquipmentReaderCloudant;
import com.aa.gsa.service.impl.EquipmentServiceImpl;
import com.aa.gsa.service.impl.FileResultsWriter;
import com.aa.gsa.service.impl.PointsServiceImpl;
import com.aa.gsa.service.impl.ScheduleQueryServiceImpl;
import com.aa.gsa.service.impl.ScheduleServiceImpl;
import com.aa.gsa.service.impl.SettingsPerGroupServiceImpl;
import com.aa.gsa.service.impl.StationCodeReaderImpl;
import com.aa.gsa.util.PointsProcessorConstants;
import com.aa.gsa.writer.CPPResultsWriter;
import com.cloudant.client.api.CloudantClient;

/**
 * Rules Processor Configuration 
 * @author 940914
 */
@Configuration
@Import({ RabbitConfig.class })
@EnableBatchProcessing
public class PointsProcessorJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	private BatchJobProperties batchJobProperties;

	@Autowired
	private MessagingProperties messagingProperties;
	
	@Autowired
	private SendGridProperties sendGridProperties;

	@Autowired
	private CloudantClient cloudantClient;

	@Autowired
	private RabbitConfig rabbitConfig;

	@Autowired
	private Job job;

	@Autowired
	private JobRepository jobRepository;

	@Bean
	public Job rulesProcessorJob() {
		return jobBuilders.get(GSA_RULES_PROCESSOR_JOB)
				.start(partitionStep())
				.listener(protocolListener())
				.listener(new RunListener(cloudantClient, rabbitConfig.template(), messagingProperties, batchJobProperties, sendGridProperties))
				.build();
	}

	@Bean
	public Step partitionStep() {
		return stepBuilders.get("partitionStep")
				.partitioner(step())
				.partitioner("processor-step", partitioner())
				.gridSize(batchJobProperties.getNoOfPartitions())
				.taskExecutor(simpleAsyncTaskExecutor())
				.build();
	}

	@Bean
	public Step step() {
		return stepBuilders.get("points-processor-step")
				.<CPP, List<CPPResult>>chunk(batchJobProperties.getChunkSize())
				.reader(reader(INT_EXPR, INT_EXPR))
				.processor(compositeProcessor())
				.faultTolerant()
				.skipPolicy(skipPolicy(INT_EXPR, STR_EXPR))
				.writer(writer(INT_EXPR))
				.listener(statusUpdateListener())
				.build();
	}

	@Bean
	@StepScope
	public SkipPolicy skipPolicy(@Value("#{stepExecution.jobExecution.id}") int jobId, @Value("#{jobParameters['"+PointsProcessorConstants.RUN_NAME+"']}") String runName) {
		return new PointsProcessorSkipPolicy(rabbitConfig.template(), messagingProperties, jobId, runName);
	}

	@Bean
	@StepScope
	public ItemReader<CPP> reader(@Value("#{stepExecutionContext['"+FROM+"']}") int from, @Value("#{stepExecutionContext['"+TO+"']}") int to) {
		return new ListItemReader<>(cppReaderNoSQLStore(STR_EXPR).findWithRange(from, to));
	}	

	@Bean
	public StationCodeReader stationCodeReader() {
		CloudantService cloudantService = new CloudantService(cloudantClient, batchJobProperties.getStationsDatabaseName());
		List<StationCode> stationCodes = cloudantService.getAllDocuments(StationCode.class);
		if (CollectionUtils.isEmpty(stationCodes)) {
			throw new PointsProcessorException("No StationCodes found");
		}
		return new StationCodeReaderImpl(stationCodes);
	}

	@Bean
	public ScheduleService scheduleService() {
		return new ScheduleServiceImpl();
	}
	
	@Bean
	@StepScope
    public EligibilityResultService eligibilityResultService(@Value("#{stepExecution.jobExecution.id}") int jobId) {
        return new EligibilityResultServiceImpl(new CloudantService(cloudantClient, batchJobProperties.getEligibilityResultsDatabaseName()), jobId);
    }
	
	@Bean
	public EligibilityService eligibilityService() {
		return new EligibilityServiceImpl(settingsPerGroupService(STR_EXPR), scheduleService(), eligibilityResultService(INT_EXPR));
	}

	@Bean
	@StepScope
	public ScheduleQueryService scheduleQueryService(@Value("#{jobParameters['"+SCHEDULE_FILE_ID+"']}") String scheduleFileId) {
		return new ScheduleQueryServiceImpl(cloudantClient, scheduleFileId);
	}

	@Bean
	@StepScope
	public CPPService cppService() {
		return new CPPServiceImpl(stationCodeReader(), scheduleQueryService(null), settingsPerGroupService(STR_EXPR));
	}

	@Bean
	@StepScope
	public SettingsPerGroupService settingsPerGroupService(@Value("#{jobParameters['"+SETTINGS_DOCUMENT_NAME+"']}") String settingsDocName) {
		return new SettingsPerGroupServiceImpl(cloudantClient, batchJobProperties.getSettingsDatabaseName(), settingsDocName);
	}

	@Bean
	public EquipmentReader equipmentReader() {
		return new EquipmentReaderCloudant(cloudantClient, batchJobProperties.getEquipmentCodesDatabaseName());
	}

	@Bean
	@StepScope
	public ItemProcessor<CPP, List<CPPResult>> compositeProcessor() {
		final CompositeItemProcessor<CPP, List<CPPResult>> compositeItemProcessor = new CompositeItemProcessor<>();

		List<ItemProcessor<?, ?>> delegates = new ArrayList<>();

		//initialize processing
		delegates.add(new InitProcessor(cppService(), scheduleService()));

		//remove in-eligible schedules applying filters
		delegates.add(new ScheduleFilterProcessor(serviceTypeFilter(), true));
		delegates.add(new ScheduleFilterProcessor(groundTimeFilter())); // this should be called before Circuity Filter
		delegates.add(new ScheduleFilterProcessor(circuityFilter()));

		//evaluate eligibility
		delegates.add(new EligibilityProcessor(eligibilityService(), eligibilityResultService(INT_EXPR)));

		//calculate points
		delegates.add(new PointsProcessor(pointsService()));

		//populate result
		delegates.add(new FinalProcessor());	

		compositeItemProcessor.setDelegates(delegates);

		return compositeItemProcessor;
	}

	@Qualifier("serviceTypeFilter")
	@Bean
	public EligibilityFilter serviceTypeFilter() {
		return new ServiceLevelFilter(eligibilityResultService(INT_EXPR));
	}

	@Qualifier("groundTimeFilter")
	@Bean
	@StepScope
	public EligibilityFilter groundTimeFilter() {
		return new GroundTimeFilter(scheduleService(), eligibilityResultService(INT_EXPR), settingsPerGroupService(STR_EXPR).groundTimeSettings());
	}

	@Qualifier("circuityFilter")
	@Bean
	@StepScope
	public EligibilityFilter circuityFilter() {
		return new CircuityFilter(settingsPerGroupService(STR_EXPR).circuitySettings(), eligibilityResultService(INT_EXPR), settingsPerGroupService(STR_EXPR).groundTimeSettings());
	}	

	@Bean
	@StepScope
	public PointsService pointsService() {
		return new PointsServiceImpl(settingsPerGroupService(STR_EXPR), equipmentService(), scheduleService());
	}

	@Bean
	public EquipmentService equipmentService() {
		return new EquipmentServiceImpl(equipmentReader());
	}

	@Bean
	@StepScope
	public Partitioner partitioner() {
		return new CPPPartioner(cppReaderNoSQLStore(STR_EXPR).getCount());
	}

	@Bean
	public TaskExecutor simpleAsyncTaskExecutor() {
		final SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor(GSA_RULES_PROCESSOR_JOB);
		asyncTaskExecutor.setConcurrencyLimit(batchJobProperties.getNoOfPartitions());
		return asyncTaskExecutor;
	}

	@Qualifier("cppReaderNoSQLStore")
	@Bean
	@StepScope
	public CPPReader cppReaderNoSQLStore(@Value("#{jobParameters['"+CPP_FILE_ID+"']}") String cppFileId) {
		return new CPPReaderNoSQLStore(cloudantClient, cppFileId);
	}

	@Bean
	public ResultsWriter resultsWriter() {
		return new FileResultsWriter("results.json");
	}

	@Bean
	@StepScope
	public ItemWriter<List<CPPResult>> writer(@Value("#{stepExecution.jobExecution.id}") int jobId) {
		if (System.getProperty("spring.profiles.active") == null || System.getProperty("spring.profiles.active").equals("local")) {
			return new CPPResultsWriter(new CloudantService(cloudantClient, "cpp-results-test"), jobId);
		}
		return new CPPResultsWriter(new CloudantService(cloudantClient, batchJobProperties.getCppResultsDatabaseName()), jobId);
	}

	@Bean
	public ProtocolListener protocolListener() {
		return new ProtocolListener();
	}

	@Bean
	@StepScope
	public StatusUpdateListener statusUpdateListener() {
		StatusUpdateListener statusUpdateListener = new StatusUpdateListener(rabbitConfig.template());
		statusUpdateListener.setExchangeName(messagingProperties.getLogExchange());
		statusUpdateListener.setChunkSize(batchJobProperties.getChunkSize());
		statusUpdateListener.setTotalNoOfMarkets(cppReaderNoSQLStore(STR_EXPR).getCount());
		return statusUpdateListener; 
	}

	@Bean
	public SimpleMessageListenerContainer runListener(ConnectionFactory connectionFactory) throws AmqpException, IOException {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(rabbitConfig.runQueue());
		container.setMessageConverter(rabbitConfig.jackson2MessageConverter());
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setDefaultRequeueRejected(false);
		container.setMessageListener(new RunMessageProcessor(job, jobRepository, cloudantClient, rabbitConfig.template(), messagingProperties, batchJobProperties));
		return container;
	}
}