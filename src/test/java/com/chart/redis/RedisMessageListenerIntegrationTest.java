package com.chart.redis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.chart.redis.queue.Receiver;
import com.chart.redis.queue.RedisMessagePublisher;

@SpringBootTest
public class RedisMessageListenerIntegrationTest {
	//@Autowired
	//private RedisMessagePublisher redisMessagePublisher;
	
	@Autowired
	StringRedisTemplate template;
	
	@Autowired
	private Receiver receiver;
	
	@Value( "${spring.redis.host}" )
	private String host;

	@Test
	public void testOnMessage() throws Exception {
		System.out.println("host:"+host);
		String message = "Message " + UUID.randomUUID();
		
		
		while (receiver.getCount() == 0) {

			System.out.println("Sending message...");
			template.convertAndSend("chat", "Hello from Redis!");
			Thread.sleep(5000L);
		}
		
		System.exit(0);
		/*
		 * redisMessagePublisher.publish(message); Thread.sleep(2000);
		 * System.out.println("aaaa :"+RedisMessageSubscriber.msg);
		 * assertTrue(RedisMessageSubscriber.msg.equals(message));
		 */
	}
}
