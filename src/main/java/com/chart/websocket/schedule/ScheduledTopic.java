package com.chart.websocket.schedule;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chart.websocket.OutputMessage;
import com.github.javafaker.Faker;

@Service
public class ScheduledTopic {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTopic.class);
	private final SimpMessagingTemplate simpMessagingTemplate;

	private final Faker faker;

	public ScheduledTopic(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
		faker = new Faker();
	}

	//@Scheduled(fixedRate = 5000)
	public void sendPushmessages() {
		
		final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		log.info("sendPushmessages ==>" + time);
		List<OutputMessage> list = Arrays.asList(new OutputMessage("Chuck Norris", faker.chuckNorris().fact(), time));
		simpMessagingTemplate.convertAndSend("/topic/pushmessages", list);
	}

	//@Scheduled(fixedRate = 1000)
	public void sendChart() {
		
		final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		//log.info("sendChart ==>" + time);
		List<OutputMessage> list = Arrays.asList(new OutputMessage("Chuck Norris", faker.chuckNorris().fact(), time));
		simpMessagingTemplate.convertAndSend("/topic/chart", list);
		//throw new RuntimeException("aa is not null");
	}

}
