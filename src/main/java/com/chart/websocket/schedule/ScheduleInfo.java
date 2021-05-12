package com.chart.websocket.schedule;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

public class ScheduleInfo {
	private String scheduleId;
	private Consumer<String> action;
	private long period;
	private ScheduledFuture<?> task;
	

	public Consumer<String> getAction() {
		return action;
	}

	public void setAction(Consumer<String> action) {
		this.action = action;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public ScheduledFuture<?> getTask() {
		return task;
	}

	public void setTask(ScheduledFuture<?> task) {
		this.task = task;
	}
}
