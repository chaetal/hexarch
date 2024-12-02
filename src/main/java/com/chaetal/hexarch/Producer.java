package com.chaetal.hexarch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
public class Producer {
    public void send(String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, this.topic);
        this.kafkaTemplate.send(this.topic, payload);
    }

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public Producer(
            @Value("${topic.out}")
            String topic,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Component
    public static class InProducer extends Producer {
        public InProducer(
                @Value("${topic.in}")
                String topic,
                KafkaTemplate<String, String> kafkaTemplate
        ) {
            super(topic, kafkaTemplate);
        }
    }

    @Component
    public static class OutProducer extends Producer {
        public OutProducer(
                @Value("${topic.out}")
                String topic,
                KafkaTemplate<String, String> kafkaTemplate
        ) {
            super(topic, kafkaTemplate);
        }
    }


}
