package com.aa.gsa.enums;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SAEFTtest {
	@Test
	public void findByValueTest() {
		assertEquals(SAEFT.WITHIN_30, SAEFT.findBy(10, 10));
		assertEquals(SAEFT.BETWEEN_31_45, SAEFT.findBy(10, 41));
		assertEquals(SAEFT.MORE_THAN_90, SAEFT.findBy(10, 9000));
	}
}
