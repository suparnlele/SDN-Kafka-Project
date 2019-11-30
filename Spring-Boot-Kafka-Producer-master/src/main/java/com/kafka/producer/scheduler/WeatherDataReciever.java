package com.kafka.producer.scheduler;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.common.errors.InvalidTopicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.kafka.producer.model.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WeatherDataReciever {
	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	 private final AtomicLong counter = new AtomicLong();
	
	
	@Autowired
	private KafkaTemplate<String,Message> kafkaTemplate;
	
	@Scheduled(fixedRate = 6000)
	  public void getDataAndProduce() {
		String logHead="getDataAndProduce() :: "+dateFormat.format(new Date())+" :: ";
	   // log.info("The time is now {}", dateFormat.format(new Date()));
		
		try {
		RestTemplate restTemplate = new RestTemplate();
		String response=restTemplate.getForObject("https://samples.openweathermap.org/data/2.5/weather?q=bangalore&appid=b6907d289e10d714a6e88b30761fae22", String.class);
		Message message=new Message();
		message.setMsgKey(""+counter.incrementAndGet());
		message.setMsgVal(response);
		log.info(logHead+"Data :: "+message);
		kafkaTemplate.send("DATABUS",message.getMsgKey(),message);
		log.info(logHead+"Data send to Topic");
		}catch(InvalidTopicException te) {
			log.error("InvalidTopicException :: "+te.getMessage());
		}catch(Exception e) {
			log.error("Exception :: "+e.getMessage());
		}
	  }
}
