package com.chart.redis.queue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.chart.websocket.OutputMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class RedisMessageSubscriber implements MessageListener {

	@Autowired
	Environment environment;

	// Port via annotation
	@Value("${server.port}")
	int aPort;

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageSubscriber.class);

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	// public static String msg = "";
	public void onMessage(final Message message, final byte[] pattern) {
		LOGGER.info(message.toString());
		LOGGER.info(new String(pattern));
		LOGGER.info("aPort = " + aPort);
		String port = environment.getProperty("server.port");
		LOGGER.info("port = " + port);
		// 역직렬화
		Gson gson = new GsonBuilder().create();
		com.chart.websocket.Message msg1 = gson.fromJson(new String(message.getBody()),
				com.chart.websocket.Message.class);
		final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

		List<OutputMessage> list = Arrays.asList(new OutputMessage(
				"[" + msg1.getHostName() + " : " + msg1.getIp() + "]" + msg1.getFrom(), msg1.getText(), time));
		simpMessagingTemplate.convertAndSend("/topic/" + new String(pattern), list);

	}
}
