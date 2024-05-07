package io.glitchtech.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

public class Dispatcher implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(DispatcherDemo.class.getName());
    private String fileLocation;
    private String topicName;
    private KafkaProducer<Integer, String> producer;

    public Dispatcher(String fileLocation, String topicName, KafkaProducer<Integer, String> producer) {
        this.fileLocation = fileLocation;
        this.topicName = topicName;
        this.producer = producer;
    }

    @Override
    public void run() {
        logger.info("Start processing " + fileLocation);
        File file = new File(fileLocation);
        int lineCount = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                producer.send(new ProducerRecord<>(topicName, null, line));
                lineCount++;
            }
            logger.info("Finished Sending " + lineCount + " messages from " + fileLocation);
        } catch (Exception exception) {
            System.out.printf(exception.getMessage());;
        }
    }
}
