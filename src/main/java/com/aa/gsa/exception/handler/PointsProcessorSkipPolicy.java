package com.aa.gsa.exception.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import com.aa.gsa.domain.StatusMessage;
import com.aa.gsa.enums.StatusMessageType;
import com.aa.gsa.exception.CircuitySettingNotFoundException;
import com.aa.gsa.exception.InvalidCityCodeException;
import com.aa.gsa.exception.InvalidGroupException;
import com.aa.gsa.exception.InvalidGroupTypeException;
import com.aa.gsa.exception.InvalidMinServiceLevelException;
import com.aa.gsa.exception.InvalidNumberOfStopsException;
import com.aa.gsa.exception.InvalidStationCodeException;
import com.aa.gsa.exception.InvalidTravelTypeException;
import com.aa.gsa.exception.ODKeyNotFoundException;
import com.aa.gsa.exception.UnknownServiceLevelException;
import com.aa.gsa.exception.ViewQueryException;
import com.aa.gsa.properties.MessagingProperties;

public class PointsProcessorSkipPolicy implements SkipPolicy {

	private Log log = LogFactory.getLog(PointsProcessorSkipPolicy.class);

	private RabbitTemplate rabbitTemplate;

	private MessagingProperties msgProperties;

	private long runId;
	
	private String runName;
	/**
	 * Skip Count for all known exceptions
	 */
	private static final int KNOWN_EXCEPTIONS_SKIP_COUNT = 100;

	/**
	 * Skip count for Cloudant environment related exceptions
	 */
	private static final int CLOUDANT_EXCEPTIONS_SKIP_COUNT = 50;
	
	public PointsProcessorSkipPolicy(RabbitTemplate rabbitTemplate, MessagingProperties msgProperties, long runId, String runName) {
		this.rabbitTemplate = rabbitTemplate;
		this.msgProperties = msgProperties;
		this.runId = runId;
		this.runName = runName;
	}

	@Override
	public boolean shouldSkip(Throwable throwable, int skipCount) throws SkipLimitExceededException {
		if ((throwable instanceof InvalidMinServiceLevelException ||
			 throwable instanceof InvalidGroupException ||	
			 throwable instanceof InvalidGroupTypeException || 
			 throwable instanceof InvalidTravelTypeException || 
			 throwable instanceof InvalidCityCodeException ||
			 throwable instanceof InvalidStationCodeException ||
			 throwable instanceof CircuitySettingNotFoundException ||
			 throwable instanceof ODKeyNotFoundException ||
			 throwable instanceof InvalidNumberOfStopsException ||
			 throwable instanceof UnknownServiceLevelException) 
				&& skipCount < KNOWN_EXCEPTIONS_SKIP_COUNT) {
			log.info(throwable.getMessage());
			rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, errorMsg(throwable.getMessage()));
			return true;
		}

		if (throwable instanceof ViewQueryException && skipCount < CLOUDANT_EXCEPTIONS_SKIP_COUNT) {
			log.info(throwable.getMessage());
			rabbitTemplate.convertAndSend(msgProperties.getLogExchange(), null, errorMsg(throwable.getMessage()));
			return true;
		}

		return false;
	}

	public StatusMessage errorMsg(String message) {
		StatusMessage statusMessage = new StatusMessage();
		statusMessage.setMessage(message);
		statusMessage.setRunId(runId);
		statusMessage.setRunName(runName);
		statusMessage.setMessageType(StatusMessageType.ERROR.name());
		return statusMessage;
	}
}
