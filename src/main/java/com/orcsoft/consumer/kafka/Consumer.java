package com.orcsoft.consumer.kafka;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "CUSTOMER_TOPIC", groupId = "CUSTOMER_TOPIC_GROUP")
    public void execute(String message) {
        logger.info("Consumer execute :: message={}, timestamp={}", message, new Date());
    }

}
