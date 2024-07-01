package com.ashoppayment.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;


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

}

