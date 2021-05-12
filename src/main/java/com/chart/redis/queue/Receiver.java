package com.chart.redis.queue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.chart.websocket.Message;
import com.chart.websocket.OutputMessage;
import com.github.javafaker.Faker;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	public Receiver(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	private AtomicInteger counter = new AtomicInteger();

	public void receiveMessage(Message message) {
		counter.incrementAndGet();
		//System.out.println("Received <" + message + ">" + getCount());
		
		final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		//log.info("sendChart ==>" + time);
		List<OutputMessage> list = Arrays.asList(new OutputMessage(message.getFrom(), message.getText(), time));
		simpMessagingTemplate.convertAndSend("/topic/chart", list);
		
	}

	public int getCount() {
		return counter.get();
	}
}
