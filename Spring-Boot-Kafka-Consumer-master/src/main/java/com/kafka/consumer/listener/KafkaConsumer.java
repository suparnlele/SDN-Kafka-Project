package com.kafka.consumer.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kafka.producer.model.Message;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class KafkaConsumer {

	 private final AtomicLong counter = new AtomicLong();
	
	
	@KafkaListener(topics = "DATABUS", groupId="consumer_one",
	            containerFactory = "kafkaListenerFactoryOne")
	/*@KafkaListener(topics = "DATABUS",
			containerFactory = "kafkaListenerFactoryOne")*/
	public void consumeOne(@Payload Message msg, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		System.out.println("Consumed JSON Message: " + msg);
		log.debug("consumeOne(1) :: Consumed JSON Message:{ Broker Partition : " +partition+" | Message Key : "+key+" | Message : "+msg+" }");
	}
	@KafkaListener(topics = "DATABUS", groupId="consumer_two",
            containerFactory = "kafkaListenerFactoryTwo")
	/*@KafkaListener(topics = "DATABUS",
			containerFactory = "kafkaListenerFactoryTwo")*/
	public void consumeTwo(@Payload Message msg, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		System.out.println("Consumed JSON Message: " + msg);
		log.debug("consumeTwo(2) :: Consumed JSON Message:{ Broker Partition : " +partition+" | Message Key : "+key+" | Message : "+msg+" }");
		
	}

	@KafkaListener(topics = "DATABUS", groupId="consumer_three",
containerFactory = "kafkaListenerFactoryThree")
	/*@KafkaListener(topics = "DATABUS",
			containerFactory = "kafkaListenerFactoryThree")*/
	public void consumeThree(@Payload Message msg, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		System.out.println("Consumed JSON Message: " + msg);
		StringBuilder sb=new StringBuilder();
		sb.append("consumeThree(3) :: Consumed JSON Message:{ Broker Partition : " +partition+" | Message Key : "+key+" | Message : "+msg+" }");
		//log.debug("consumeThree(3) :: Consumed JSON Message:{ Broker Partition : " +partition+" | Message Key : "+key+" | Message : "+msg+" }");
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id",key);
		String uri="http://localhost:9200/weather/databus/{id}/_create";
		sb.append("Consumed JSON Message:{ elasticsearch URL :: "+uri+" } | { id : "+params.get("id")+" } Data Sent To Elasticsearch.");
		log.debug(sb.toString());
		sb=null;
	    restTemplate.put(uri, msg, params);
	}

}
