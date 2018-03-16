package com.aa.gsa.listener;

import static com.aa.gsa.domain.StatusMessage.errorMsg;
import static com.aa.gsa.domain.StatusMessage.infoMsg;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import com.aa.gsa.domain.Run;
import com.aa.gsa.enums.RunStatus;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.MessagingProperties;
import com.aa.gsa.properties.SendGridProperties;
import com.aa.gsa.util.PointsProcessorConstants;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;


/**
 * @author 940914
 */
public class RunListener implements JobExecutionListener {

	private Database runDatabase;	

	private RabbitTemplate rabbitTemplate;

	private MessagingProperties msgProperties;

	private BatchJobProperties props;
	
	private SendGridProperties sendGridProperties; 
	
	private SendGrid sendgrid;
	
	public RunListener(CloudantClient cloudantClient, RabbitTemplate rabbitTemplate, MessagingProperties msgProperties, BatchJobProperties props, SendGridProperties sendGridProperties) {
		this.rabbitTemplate = rabbitTemplate;
		this.props = props;
		this.msgProperties = msgProperties;
		this.sendGridProperties = sendGridProperties;
		runDatabase = cloudantClient.database(this.props.getRunDatabaseName(), false);
		sendgrid = new SendGrid(sendGridProperties.getApiKey());
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		String runDocId = jobExecution.getJobParameters().getString(PointsProcessorConstants.RUN_DOCUMENT_ID);
		Run run = runDatabase.find(Run.class, runDocId);
		run.setRunId(jobExecution.getId().toString());
		run.setStatus(RunStatus.RUNNING.getStatus());
		runDatabase.update(run);
		rabbitTemplate.convertAndSend(msgProperties.getRunStatusUpdateExchange(), null, run);
		rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, infoMsg("Started the run "+run.getName(), run));
		
        SendGrid.Email email = new SendGrid.Email();
        email.addTo(run.getRunByEmail());
        email.setFrom(sendGridProperties.getFromEmail());
        email.setSubject("GAAP run started with Run ID:" + " " + run.getRunId());
        email.setHtml("Hello, "                  + run.getRunBy() + 
        			  "<br/>"                    + 
        			  "<br/> Run started for GAAP application with below details" + 
        			  "<br/>"                    +
        			  "<br/> Run ID:"            + " " +run.getRunId() +
        			  "<br/> Run Name:"          + " " +run.getName() +
        			  "<br/> Run Status:"        + " " +run.getStatus() +
        			  "<br/> Run Submitted By:"  + " " +run.getSubmittedBy() +
        			  "<br/> Run Submitted On:"  + " " +run.getSubmittedOn());
        try {
			SendGrid.Response response = sendgrid.send(email);
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		ExitStatus exitStatus = jobExecution.getExitStatus();
		Run run = runDatabase.find(Run.class, jobExecution.getJobParameters().getString(PointsProcessorConstants.RUN_DOCUMENT_ID));
		if (exitStatus.getExitCode() == ExitStatus.COMPLETED.getExitCode()) {
			run.setStatus(RunStatus.COMPLETED.getStatus());
			rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, infoMsg("Completed the run "+run.getName(), run));
      
	        SendGrid.Email email = new SendGrid.Email();
	        email.addTo(run.getRunByEmail());
	        email.setFrom(sendGridProperties.getFromEmail());
	        email.setSubject("GAAP run completed with Run ID:" + " " + run.getRunId());
	        email.setHtml("Hello, "                  + run.getRunBy() + 
	        			  "<br/>"                    + 
	        			  "<br/> Run completed for GAAP application with below details" + 
	        			  "<br/>"                    +
	        			  "<br/> Run ID:"            + " " +run.getRunId() +
	        			  "<br/> Run Name:"          + " " +run.getName() +
	        			  "<br/> Run Status:"        + " " +run.getStatus() +
	        			  "<br/> Run Submitted By:"  + " " +run.getSubmittedBy() +
	        			  "<br/> Run Submitted On:"  + " " +run.getSubmittedOn());
	        try {
				SendGrid.Response response = sendgrid.send(email);
			} catch (SendGridException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
		} else if (exitStatus.getExitCode() == ExitStatus.FAILED.getExitCode()) {
			run.setStatus(RunStatus.FAILED.getStatus());
			List<Throwable> errors = jobExecution.getAllFailureExceptions();
			boolean isKnownError = false;
			for (Throwable error : errors) {
				if (error instanceof PointsProcessorException) {
					rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, errorMsg(error.getMessage(), run));
					isKnownError = true;
				} else {
					Throwable rootCause = ExceptionUtils.getRootCause(error);
					if (rootCause instanceof PointsProcessorException) {
						rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, errorMsg(rootCause.getMessage(), run));
						isKnownError = true;
					}
				}
			}
			
			String failureReason = "Run name:" + " " +run.getName()+ " failed";	
			
			if (!isKnownError) {
				rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, errorMsg("System error occured. Please contact IT support.", run));
				failureReason = "System error occured. Please contact IT support.";
			}
			rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, infoMsg(run.getName()+ " failed", run));
			

	        SendGrid.Email email = new SendGrid.Email();
	        email.addTo(run.getRunByEmail());
	        email.setFrom(sendGridProperties.getFromEmail());
	        email.setSubject("GAAP run failed with Run ID:" + " " + run.getRunId());
	        email.setHtml("Hello, "                  + run.getRunBy() + 
	        			  "<br/>"                    + 
	        			  "<br/> Run failed for GAAP application with below details" + 
	        			  "<br/>"                    +
	        			  "<br/> Run ID:"            + " " +run.getRunId() +
	        			  "<br/> Run Name:"          + " " +run.getName() +
	        			  "<br/> Run Status:"        + " " +run.getStatus() +
	        			  "<br/> Run Submitted By:"  + " " +run.getSubmittedBy() +
	        			  "<br/> Run Submitted On:"  + " " +run.getSubmittedOn());
	        try {
				SendGrid.Response response = sendgrid.send(email);
			} catch (SendGridException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		runDatabase.update(run);
		rabbitTemplate.convertAndSend(msgProperties.getRunStatusUpdateExchange(), null, run);
	}
}
