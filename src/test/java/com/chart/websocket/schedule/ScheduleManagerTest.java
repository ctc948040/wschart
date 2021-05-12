package com.chart.websocket.schedule;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleManagerTest {
	private static final Logger log = LoggerFactory.getLogger(ScheduleManagerTest.class);
	@Autowired
	ScheduleManager scheduleManager;
	
//	@Autowired
//	private ScheduledAnnotationBeanPostProcessor postProcessor;
	
	//@Autowired 
	//private ThreadPoolTaskScheduler taskScheduler;

	@Test
	void registerTest() {
		assertNotNull(scheduleManager);
		
		//taskScheduler.initialize();
		String mySchedulerId1 = "myScheduler1";
		String mySchedulerId2 = "myScheduler2";
		scheduleManager.register(mySchedulerId1, scheduleId -> {
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			log.info("hello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
		}, 1000);
		
		//taskScheduler.shutdown();
		
		scheduleManager.register(mySchedulerId2, scheduleId -> {
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			log.info("reHello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
		}, 1000);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			//
		}
		
//		try {
//			task.get();
//		} catch (InterruptedException | ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Test
	void stopTest() {
		assertNotNull(scheduleManager);
		
		String mySchedulerId1 = "myScheduler1";
		String mySchedulerId2 = "myScheduler2";
		scheduleManager.register(mySchedulerId1, scheduleId -> {
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			log.info("hello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
		}, 1000);
		
//		scheduleManager.register(mySchedulerId2, scheduleId -> {
//			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
//			log.info("reHello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
//		}, 1000);
		
		assertThrows(TimeoutException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				scheduleManager.getTask(mySchedulerId1).get(2,TimeUnit.SECONDS);				
			}
		});
		
		scheduleManager.stopTask(mySchedulerId1);
		assertThrows(CancellationException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				scheduleManager.getTask(mySchedulerId1).get(2,TimeUnit.SECONDS);				
			}
		});
		
		scheduleManager.restartTask(mySchedulerId1);
		assertThrows(TimeoutException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				scheduleManager.getTask(mySchedulerId1).get(5,TimeUnit.SECONDS);				
			}
		});
		
		log.info("end===========>");
	}
	
	@Test
	void stopAllTest(){
		assertNotNull(scheduleManager);
		
		String mySchedulerId1 = "myScheduler1";
		String mySchedulerId2 = "myScheduler2";
		scheduleManager.register(mySchedulerId1, scheduleId -> {
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			log.info("hello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
		}, 1000);
		
		scheduleManager.stopTask(mySchedulerId1);
		
		scheduleManager.register(mySchedulerId1, scheduleId -> {
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			log.info("hello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
		}, 1000);
		
		scheduleManager.register(mySchedulerId2, scheduleId -> {
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			log.info("reHello : {} {} : {}.", scheduleId,Thread.currentThread().getName(), time);
		}, 1000);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			//
		}
		
		scheduleManager.stopAll();
		
		assertThrows(CancellationException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				scheduleManager.getTask(mySchedulerId1).get();				
			}
		});
	}

}
