package com.chaetal.hexarch;

import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class EmbeddedKafkaConfig {
    //@Bean
    //@Profile("dev")
    //EmbeddedKafkaBroker broker() {
    //    Map<String, String> properties = new HashMap<>();
    //    properties.put("listeners", "PLAINTEXT://localhost:9092,REMOTE://10.0.0.20:9093");
    //    properties.put("advertised.listeners", "PLAINTEXT://localhost:9092,REMOTE://10.0.0.20:9093");
    //    properties.put("listener.security.protocol.map", "PLAINTEXT:PLAINTEXT,REMOTE:PLAINTEXT");
    //    return new EmbeddedKafkaZKBroker(1)
    //            .kafkaPorts(9092)
    //            .brokerProperties(properties)
    //            .brokerListProperty("spring.kafka.bootstrap-servers");
    //}
}
