package io.glitchtech.kafka.producer;

import io.glitchtech.kafka.config.AppConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class HelloProducer {
    private static final Logger logger = LoggerFactory.getLogger(HelloProducer.class.getSimpleName());
    public static void main(String[] args) {
        logger.info("Creating Kafka Producer...");

        // Create Producer
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);

        // send messages to Kafka
        for (int i = 0; i < AppConfigs.numEvents; i++){
            // producer record takes topic, key and value
            ProducerRecord producerRecord = new ProducerRecord(AppConfigs.topicName, i, "Simple Message-" + i);
            producer.send(producerRecord);
        }
        logger.info("Finished Sending messages. Closing producer");
        producer.close();
    }
}
