package com.betvictor.processing.kafka.config;

import com.betvictor.processing.text.TextResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

//    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
//    private String bootstrapAddress;

//    public ConsumerFactory<String, TextResponse> wordProcessedConsumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "word.processed");
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(TextResponse.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, TextResponse> wordProcessedListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, TextResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(wordProcessedConsumerFactory());
//        return factory;
//    }
}
