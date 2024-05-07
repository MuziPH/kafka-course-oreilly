package io.glitchtech.kafka.config;

import org.apache.kafka.common.protocol.types.Field;

public class AppConfigs {
    public final static String applicationID = "Multi-Threaded-Producer";
    public final static String topicName = "nse-eod-topic";
    public final static String kafkaConfigFileLocation = "multithreaded-producer/kafka.properties";
    public final static String[] eventFiles = {"multithreaded-producer/data/NSE05NOV2018BHAV.csv","multithreaded-producer/data/NSE06NOV2018BHAV.csv"};

}
