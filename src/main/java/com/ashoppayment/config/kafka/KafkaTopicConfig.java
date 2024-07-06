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
    private String changeOrdersStatusTopic = "change_orders_status";

    private int numPartitions =3;

    @Bean
    public NewTopic newOrdersTopic() {
        return TopicBuilder
                .name(newOrderTopic)
                .partitions(numPartitions)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic newPaymentTopic() {
        return TopicBuilder
                .name(newPaymentTopic)
                .build();
    }

    @Bean
    public NewTopic changeOrdersStatusTopic(){
        return TopicBuilder
                .name(changeOrdersStatusTopic)
                .build();
    }
}

