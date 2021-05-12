package com.chart.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.chart.redis.queue.RedisMessageSubscriber;

@Configuration
public class RedisConfig {
	
	//@Autowired
	//SimpMessagingTemplate simpMessagingTemplate;
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,RedisMessageSubscriber redisMessageSubscriber) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// container.addMessageListener(listenerAdapter, new PatternTopic("chart"));
		container.addMessageListener(messageListener(redisMessageSubscriber), new PatternTopic("chart"));

		return container;
	}

	@Bean
	MessageListenerAdapter messageListener(RedisMessageSubscriber redisMessageSubscriber) {
		return new MessageListenerAdapter(redisMessageSubscriber);
	}

	@Bean
	public RedisTemplate<String, Object> template(RedisConnectionFactory connectionFactory) {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(connectionFactory);
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	/*
	 * @Bean StringRedisTemplate template(RedisConnectionFactory connectionFactory)
	 * { return new StringRedisTemplate(connectionFactory); }
	 */

//	@Bean
//	MessageListenerAdapter listenerAdapter(Receiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}

	/*
	 * @Bean Receiver receiver(SimpMessagingTemplate simpMessagingTemplate) { return
	 * new Receiver(simpMessagingTemplate); }
	 */
}
