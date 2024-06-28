package com.ashoppayment.config.kafka;

import com.ashoppayment.model.dto.CreateOrderRequestDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaTopicConfig {

    private String newOrderTopic = "new_orders";
    private String newPaymentTopic = "payed_orders";

    @Bean
    public NewTopic newOrdersTopic() {
        return TopicBuilder
                .name(newOrderTopic)
                .build();
    }

    @Bean
    public NewTopic newPaymentTopic() {
        return TopicBuilder
                .name(newPaymentTopic)
                .build();
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateOrderRequestDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CreateOrderRequestDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }
    @Bean
    public ConsumerFactory<String, CreateOrderRequestDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new ErrorHandlingDeserializer<>(new StringDeserializer()),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(CreateOrderRequestDto.class)));
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        // Настройка обработчика ошибок с фиксированным BackOff
        return new DefaultErrorHandler(new FixedBackOff(1000L, 3));
    }

}

