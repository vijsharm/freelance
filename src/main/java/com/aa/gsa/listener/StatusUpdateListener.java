package com.aa.gsa.listener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

import com.aa.gsa.domain.StatusMessage;
import com.aa.gsa.util.PointsProcessorConstants;

public class StatusUpdateListener implements ChunkListener {

	private RabbitTemplate rabbitTemplate;

	/**
	 * Rabbit exchangeName
	 */
	private String exchangeName;
	
	/**
	 * Commit chunk size
	 */
	private int chunkSize;
	
	/**
	 * Total number of CPP records
	 */
	private int totalNoOfMarkets;
	
	public StatusUpdateListener(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void afterChunk(ChunkContext context) {
		Long runId = context.getStepContext().getStepExecution().getJobExecution().getId();
		String runName = context.getStepContext().getJobParameters().get(PointsProcessorConstants.RUN_NAME).toString();
        rabbitTemplate.convertAndSend(exchangeName, null, StatusMessage.progressMsg(chunkSize, totalNoOfMarkets, runId, runName));
	}

	@Override
	public void beforeChunk(ChunkContext context) {}


	@Override
	public void afterChunkError(ChunkContext context) {
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public void setTotalNoOfMarkets(int totalNoOfMarkets) {
		this.totalNoOfMarkets = totalNoOfMarkets;
	}	
}
