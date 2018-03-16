package com.aa.gsa.partitioner;

import static com.aa.gsa.util.PointsProcessorConstants.FROM;
import static com.aa.gsa.util.PointsProcessorConstants.PARTITION;
import static com.aa.gsa.util.PointsProcessorConstants.TO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class CPPPartioner implements Partitioner {
	
	private int cppCount;
	
	public CPPPartioner(int cppCount) {
		this.cppCount = cppCount;
	}
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		final Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		final int range = cppCount / gridSize;

		int from = 1;
		int to = range;
		int count = 0;
		
		for (int i=1; i <= gridSize; i++) {
			final ExecutionContext value = new ExecutionContext();
			value.putInt(FROM, from);
			value.putInt(TO, to);
			result.put(PARTITION + i, value);

			from = to + 1;
			to += range;
			
			count++;
		}
		
		//adjust the final partition to fit into gridSize.
		if (cppCount % gridSize > 0) { 
			int finalFrom = result.get(PARTITION + count).getInt(FROM);
			int finalTo  = from + cppCount % gridSize - 1;
			ExecutionContext executionContext = new ExecutionContext();
			executionContext.put(FROM, finalFrom);
			executionContext.put(TO, finalTo);
			result.put(PARTITION+count, executionContext);
		}

		return result;
	}
}
