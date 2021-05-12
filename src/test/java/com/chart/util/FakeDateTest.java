package com.chart.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.chart.rest.repositories.IndexPriceRepository;
import com.chart.util.FakeDate;

@SpringBootTest
class FakeDateTest {

	@Autowired
	IndexPriceRepository IndexPriceRepository;

	private static final Logger log = LoggerFactory.getLogger(FakeDateTest.class);

	@Autowired
	FakeDate fakeDate;

	@Test
	void test() {
		long epoch = 1601510400;
		Date addMonthDate = DateUtils.addMonths(new Date(epoch * 1000), 1);
		long currentEpoch = addMonthDate.getTime() / 1000;
		String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
				.format(new java.util.Date(currentEpoch * 1000));
		log.info(currentEpoch + ":" + date);
	}

	@Test
	void test1() {
		assertNotNull(fakeDate);

		log.info("getSimullEpoch:" + fakeDate.getSimulEpoch());

		log.info("getCnt:" + FakeDate.getCnt());

	}

}
