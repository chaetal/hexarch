package com.chaetal.hexarch;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.Duration;
import java.util.HashMap;

import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class KafkaBDD {
    public static KafkaTopicUnderTest then(KafkaTopicUnderTest topic) {
        return topic;
    }


    public static KafkaBDD.KafkaTopicUnderTest kafkaTopicUsingIn(
            String topic, String group, EmbeddedKafkaBroker embeddedKafkaBroker
    ) {
        return new KafkaTopicUnderTest(embeddedKafkaBroker, topic, group);
    }


    public static class KafkaTopicUnderTest {

        public void shouldReceive(Matcher<String> argumentMatcher) {
            ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(
                    this.consumer,
                    this.topic,
                    Duration.ofSeconds(3)
            );
            assertThat(singleRecord.value(), is(argumentMatcher));
        }

        private final String topic;
        private final Consumer<String, String> consumer;

        public KafkaTopicUnderTest(EmbeddedKafkaBroker embeddedKafkaBroker, String topic, String group) {
            this.topic = topic;

            HashMap<String, Object> props = new HashMap<>(KafkaTestUtils.consumerProps(
                    group,
                    "true",
                    embeddedKafkaBroker
            ));
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            this.consumer = new DefaultKafkaConsumerFactory<String, String>(props).createConsumer();
            this.consumer.subscribe(singleton(topic));
        }
    }


}
