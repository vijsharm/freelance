package com.aa.gsa.service;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.enums.EquipmentType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipmentReaderTest {

	@Autowired
	private EquipmentReader equipmentReader;

	@Test
	public void readCodesTest() {
		Map<String, EquipmentType> equipmentCodes = equipmentReader.getEquipmentCodes();
		Assert.assertFalse(CollectionUtils.isEmpty(equipmentCodes));
	}	
}
