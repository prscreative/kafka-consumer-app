package com.orcsoft.consumer.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.orcsoft.consumer.kafka.Consumer;

import lombok.Data;

import javax.annotation.PostConstruct;

@Data
@Component
public class ConfigProperties {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Value("${bootstrap.servers}")
    private String bootstrapServerConfig;

    @Value("${group.id}")
    private String groupIdConfig;

    @Value("${auto.offset.reset}")
    private String autoOffsetResetConfig;

    @Value("${concurrency}")
    private String concurrencyConfig;

    @PostConstruct
    public void printConfig() {
        logger.info("ConfigProperties > bootstrapServerConfig={}, groupIdConfig={}, autoOffsetResetConfig={}, concurrencyConfig={}", 
        		getBootstrapServerConfig(), 
        		getGroupIdConfig(),
        		getAutoOffsetResetConfig(),
        		getConcurrencyConfig()
        );
    }

    /**
     * @return
     */
    public Map<String, Object> getKafkaPropertiesConfig() {
        Map<String, Object> propertiesConfig = new HashMap<>();
        propertiesConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propertiesConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propertiesConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServerConfig());
        propertiesConfig.put(ConsumerConfig.GROUP_ID_CONFIG, getGroupIdConfig());
        propertiesConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, getAutoOffsetResetConfig());
        propertiesConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        propertiesConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return propertiesConfig;
    }

    /**
     * @return
     */
    public Properties getKafkaProperties() {
        Map<String, Object> propertiesConfigMap = getKafkaPropertiesConfig();
        Properties properties = new Properties();
        for (Map.Entry<String, Object> propertiesConfig : propertiesConfigMap.entrySet()) {
            properties.put(propertiesConfig.getKey(), propertiesConfig.getValue());
        }
        return properties;
    }

}
