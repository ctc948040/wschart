package com.chart.redis.queue;

public interface MessagePublisher {
	void publish(final String message);
}
