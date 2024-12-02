package com.chaetal.hexarch;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static com.chaetal.hexarch.KafkaBDD.kafkaTopicUsingIn;
import static com.chaetal.hexarch.KafkaBDD.then;
import static org.hamcrest.CoreMatchers.equalTo;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:19092", "port=19092"}
)
class JustKafkaTests {

    @Test
    @SneakyThrows
    void testActuallySendsMessage() {
        String data = "Some message";

        this.producer.send(data);

        then(kafkaTopicUsingIn(this.outTopic, "group", this.embeddedKafkaBroker)).shouldReceive(equalTo(data));
    }


    @Autowired
    private Producer.OutProducer producer;


    @Value("${topic.out}")
    private String outTopic;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
}