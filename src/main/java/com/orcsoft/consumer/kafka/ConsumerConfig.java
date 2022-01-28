package com.orcsoft.consumer.kafka;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.orcsoft.consumer.config.ConfigProperties;

@Configuration
public class ConsumerConfig {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() throws IOException {
        logger.info("Start load config From KafkaConfig");
        Map<String, Object> props = configProperties.getKafkaPropertiesConfig();
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() throws IOException {
        String concurrency = configProperties.getConcurrencyConfig();
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(Integer.valueOf(concurrency));
        return factory;
    }

}
