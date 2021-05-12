package com.chart.redis.queue;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

//@Service
public class RedisMessagePublisher implements MessagePublisher {

	//@Autowired
	private StringRedisTemplate redisTemplate;
	//@Autowired
	private ChannelTopic topic;

	public RedisMessagePublisher() {
	}

	public RedisMessagePublisher(final StringRedisTemplate redisTemplate, final ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	public void publish(final String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}

}
