package com.aa.gsa.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.aa.gsa.domain.Payload;
import com.aa.gsa.domain.result.CPPResult;
import com.aa.gsa.enums.TravelType;

/**
 * Final Rules processor that populates the CPPResult
 * @author ivc15115adm
 * 
 */
public class FinalProcessor implements ItemProcessor<Payload, List<CPPResult>> {

	@Override
	public List<CPPResult> process(Payload payload) throws Exception {
		List<CPPResult> results = new ArrayList<>(1);
		results.add(cppResult(payload, false));

		if (payload.getSchedulesNoCodeshare() != null) {
			results.add(cppResult(payload, true));
		}

		return results;
	}

	private CPPResult cppResult(Payload payload, boolean excludeCodeshare) {
		final CPPResult cppResult = new CPPResult();
		cppResult.setItemNo(payload.getCpp().getItemNumber()); 
		cppResult.setOrig(payload.getCpp().getOriginAirport());
		cppResult.setDest(payload.getCpp().getDestinationAirport());
		cppResult.setDomestic(payload.getCpp().travelType().equals(TravelType.DOMESTIC));
		cppResult.setAirlineResults(!excludeCodeshare ? payload.getResults() : payload.getResultsNoCodeshare());

		if (excludeCodeshare) {
			cppResult.setExcludeCodeshare(excludeCodeshare);
		}
		
		return cppResult;
	}
}
