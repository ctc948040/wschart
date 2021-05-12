package com.chart.websocket.schedule;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class ScheduleManager {
	private static final Logger log = LoggerFactory.getLogger(ScheduleManager.class);
	private Map<String, ScheduleInfo> scheduledTasks = new ConcurrentHashMap<>();
	
	@Autowired(required=true)
	private TaskScheduler taskScheduler;
	
	public ScheduledFuture<?> register(String scheduleId,Consumer<String> action,long period) {
		Objects.requireNonNull(action);
		//Map<String, Object> scheduleInfoMap = new ConcurrentHashMap<>();
		
		ScheduleInfo scheduleInfo = new ScheduleInfo();
		
		ScheduledFuture<?> sf = null;
		if(this.scheduledTasks.containsKey(scheduleId)) {
			scheduleInfo = this.scheduledTasks.get(scheduleId);
			sf = scheduleInfo.getTask();
			if(!sf.isDone()) {//실행중이면
				throw new RuntimeException("실행중인 스케줄이 이미 존재합니다.[" +scheduleId+"]");
			}
		}
		
		sf = taskScheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				action.accept(scheduleId);
			}
		}, period);
		
		scheduleInfo.setAction(action);
		scheduleInfo.setPeriod(period);
		scheduleInfo.setTask(sf);
		
		this.scheduledTasks.put(scheduleId,scheduleInfo);
		
		return sf;
	}
	
	public void restartTask(String scheduleId) {
		ScheduleInfo scheduleInfo = this.scheduledTasks.get(scheduleId);
		ScheduledFuture<?> sf = scheduleInfo.getTask();
		if(!sf.isDone()) {//실행중이면
			this.stopTask(scheduleId);
		}
		
		this.register(scheduleId, scheduleInfo.getAction(), scheduleInfo.getPeriod());
		log.info(scheduleId+"를 재실행합니다.");
	}
	
	/**
	 * 스케줄 목록
	 */
	public Map<String, ScheduleInfo> getScheduledTasks() {
        return this.scheduledTasks;
    }

	public ScheduledFuture<?> getTask(String scheduleId) {
		ScheduleInfo scheduleInfo = this.scheduledTasks.get(scheduleId);
		return scheduleInfo.getTask();
	}

	/**
	 * 특정 스케줄 종료
	 * @param schedulerId
	 */
	public void stopTask(String scheduleId) {		
		ScheduleInfo scheduleInfo = this.scheduledTasks.get(scheduleId);
		scheduleInfo.getTask().cancel(true);
		log.info(scheduleId+"를 종료합니다.");
	}
	
	/**
	 * 전체 스케줄 종료
	 */
	public void stopAll() {
		
		this.scheduledTasks.forEach((k,v)->{
			log.info(k+"를 종료합니다.");
			v.getTask().cancel(true);
		});
	}
	
	/**
	 * 스케줄 삭제
	 * @param scheduleId
	 */
	public void remove(String scheduleId) {
        log.info(scheduleId+"를 삭제합니다.");
        ScheduleInfo scheduleInfo = this.scheduledTasks.remove(scheduleId);
        scheduleInfo.getTask().cancel(true);
    }
	
	/**
	 * 전체스케줄 삭제
	 * @param scheduleId
	 */
	public void removeAll() {
        
		this.scheduledTasks.forEach((k,v)->{
			log.info(k+"를 삭제합니다.");
			scheduledTasks.remove(k);
			 v.getTask().cancel(true);
		});
    }
}