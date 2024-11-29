package com.chaetal.hexarch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@Getter
public class Consumer {

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());
        this.payload = consumerRecord.toString();
        this.latch.countDown();
    }

    public void resetLatch() {
        this.latch = new CountDownLatch(1);
    }

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;
}
