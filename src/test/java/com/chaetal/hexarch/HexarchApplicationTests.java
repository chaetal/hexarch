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
import static org.hamcrest.CoreMatchers.anything;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:19092", "port=19092"}
)
@DirtiesContext
class HexarchApplicationTests {

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
        this.inProducer.send(data);

        then(kafkaTopicUsingIn(this.outTopic, "group", this.embeddedKafkaBroker)).shouldReceive(anything());
    }


    @Autowired
    private Producer.InProducer inProducer;


    @Value("${topic.out}")
    private String outTopic;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

}
