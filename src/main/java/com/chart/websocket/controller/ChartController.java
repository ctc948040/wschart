package com.chart.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chart.redis.queue.RedisMessageSubscriber;
import com.chart.websocket.Message;
import com.chart.websocket.OutputMessage;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ChartController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChartController.class);
	@Autowired(required=true)
	RedisTemplate<String, Object> template;
	
    @MessageMapping("/chart")
    @SendTo("/topic/chart")
    public List<OutputMessage> send(final Message message) throws Exception {

        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        List<OutputMessage> list = Arrays.asList(new OutputMessage(message.getFrom(), message.getText(), time));
        
        return list;
    }
    
    @MessageMapping("/chart.stop")
//    @SendTo("/topic/chart")
    public void stop(final Message message) throws Exception {
    	String ip = InetAddress.getLocalHost().getHostAddress();
		LOGGER.info("ip = " + ip);
		String hostname = InetAddress.getLocalHost().getHostName();
		LOGGER.info("hostname = " + hostname);
		// Remote address
		String r_ip = InetAddress.getLoopbackAddress().getHostAddress();
		LOGGER.info("r_ip = " + r_ip);
		String r_hostname = InetAddress.getLoopbackAddress().getHostName();
		LOGGER.info("r_hostname = " + r_hostname);
		message.setIp(ip);
		message.setHostName(hostname);
    	template.convertAndSend("chart",message);
    }

}
