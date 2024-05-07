package io.glitchtech.kafka.producer;

import io.glitchtech.kafka.config.AppConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DispatcherDemo {
    private final static Logger logger = LoggerFactory.getLogger(DispatcherDemo.class.getName());

    public static void main(String[] args) {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(AppConfigs.kafkaConfigFileLocation)) {
            properties.load(inputStream);
            properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            //properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        } catch (IOException fileNotFoundException) {
            System.out.printf(fileNotFoundException.getMessage());
        }

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);
        Thread[] dispatcherThreads = new Thread[AppConfigs.eventFiles.length];
        logger.info("Starting Dispatcher threads...");
        for (int i = 0; i < AppConfigs.eventFiles.length; i++) {
            dispatcherThreads[i] = new Thread(new Dispatcher(AppConfigs.eventFiles[i], AppConfigs.topicName, producer));
            dispatcherThreads[i].start();
        }
        try {
            for (Thread thread : dispatcherThreads) {
                thread.join();
            }
        } catch (InterruptedException interruptedException) {
            logger.error("Main Thread Interrupted");
        } finally {
            producer.close();
            logger.info("Finished Dispatcher Demo");
        }
    }
}
