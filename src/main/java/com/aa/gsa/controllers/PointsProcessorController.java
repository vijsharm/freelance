package com.aa.gsa.controllers;

import java.net.URI;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aa.gsa.domain.PointsProcessorJobInfo;
import com.aa.gsa.domain.Run;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.util.PointsProcessorConstants;

/**
 * @author 940914
 */
@RestController
@RequestMapping("/processor")
public class PointsProcessorController {

	@Autowired
	private Job job;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobExplorer jobExplorer;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> process(@RequestBody Run run) {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString(PointsProcessorConstants.SCHEDULE_FILE_ID, run.getScheduleName())
				.addString(PointsProcessorConstants.CPP_FILE_ID, run.getCppFileGroupName())
				.addString(PointsProcessorConstants.SETTINGS_DOCUMENT_NAME, run.getRulesetName())
				.addString(PointsProcessorConstants.RUN_DOCUMENT_ID, run.get_id())
				.addString(PointsProcessorConstants.RUN_NAME, run.getName())
				.addDate(PointsProcessorConstants.RUN_DATE, new Date())
				.toJobParameters();
		try {
			SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
			jobLauncher.setJobRepository(jobRepository);
			jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

			JobExecution jobExecution = jobLauncher.run(job, jobParameters);
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(jobExecution.getId())
					.toUri();

			return ResponseEntity.accepted().location(location).build();
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
			throw new PointsProcessorException("Error initializing batch", ex);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{jobId}")
	@ResponseBody
	public PointsProcessorJobInfo getJobDetails(@PathVariable String jobId) {
		final JobExecution jobExecution = jobExplorer.getJobExecution(Long.parseLong(jobId));
		PointsProcessorJobInfo jobInfo = new PointsProcessorJobInfo();
		jobInfo.setJobId(jobExecution.getId());
		jobInfo.setBatchStatus(jobExecution.getStatus());
		jobInfo.setStartTime(jobExecution.getStartTime());
		jobInfo.setJobParameters(jobExecution.getJobParameters().toProperties());
		return jobInfo;
	}
}
