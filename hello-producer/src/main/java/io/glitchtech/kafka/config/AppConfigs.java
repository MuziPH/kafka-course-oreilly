package io.glitchtech.kafka.config;

public class AppConfigs {
    public final static String applicationID = "HelloProducer";
    public final static String bootstrapServers = "localhost:9092, localhost:9093";
    public final static String topicName = "hello-producer-topic";
    public final static int numEvents = 1000000;
}
