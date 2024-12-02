package com.chaetal.hexarch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class InConsumer {

    @KafkaListener(topics = "${topic.in}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());

        this.outProducer.send(consumerRecord.toString());
    }

    private final Producer outProducer;

    public InConsumer(
            @Autowired
            Producer.OutProducer outProducer
    ) {
        this.outProducer = outProducer;
    }
}
