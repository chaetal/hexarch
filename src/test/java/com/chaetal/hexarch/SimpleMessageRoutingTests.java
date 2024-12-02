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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@DirtiesContext // TODO: [chaetal] Использовать в монета?
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:19092", "port=19092"}
)
class SimpleMessageRoutingTests {

    // Приёмочный тест:
    //   Дано: Приложение запущенно
    //   Когда: Сообщение message отправляется в очередь in
    //   Тогда: В очереди out появляется реакция на m

    // Первый тест:
    //   Дано: Приложение запущено.
    //   Когда: Сообщение message отправлено в очередь in.
    //   Тогда: В очереди out появляется какое-то сообщение.
    @Test
    @SneakyThrows
    void testSendsSomeMessageToOutWhenAMessageIsSentToIn() {
        // WHEN
        String data = "Some message";
        this.inProducer.send(this.inTopic, data);

        then(kafkaTopicUsingIn(this.outTopic, "group", this.embeddedKafkaBroker)).shouldReceive(any());
    }



    @Test
    @SneakyThrows
    void testMessageIsSentToO() {
        String data = "Some message";

        this.inProducer.send(this.inTopic, data);

        then(kafkaTopicUsingIn(this.inTopic, "group", this.embeddedKafkaBroker)).shouldReceive(equalTo(data));
    }


    //@Test
    //@SneakyThrows
    //void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() {
    //    String data = "Sending with our own simple KafkaProducer";
    //
    //    producer.send(topic, data);
    //
    //    boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
    //    assertTrue(messageConsumed);
    //    assertThat(consumer.getPayload(), containsString(data));
    //}

    //@Test
    //@SneakyThrows
    //void endless() {
    //    String data = "Sending with our own simple KafkaProducer in ENDLESS";
    //
    //    while (true) {
    //
    //    producer.send(topic, data);
    //    Thread.sleep(5 * 1000);
    //    }
    //}


    //@Autowired
    //private Consumer consumer;

    @Autowired
    private Producer inProducer;

    @Value("${topic.in}")
    private String inTopic;

    @Value("${topic.out}")
    private String outTopic;


    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
}