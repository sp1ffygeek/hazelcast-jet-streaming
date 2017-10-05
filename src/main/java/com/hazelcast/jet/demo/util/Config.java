package com.hazelcast.jet.demo.util;

import com.hazelcast.jet.demo.serialization.ConsumptionDeserializer;
import com.hazelcast.jet.demo.serialization.ConsumptionSerializer;
import java.util.Properties;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

/**
 * date: 9/29/17
 * author: emindemirci
 */
public class Config {

    private Config() {
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("group.id", "hz-kafka");
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", LongSerializer.class.getCanonicalName());
        properties.setProperty("key.deserializer", LongDeserializer.class.getCanonicalName());
        properties.setProperty("value.serializer", ConsumptionSerializer.class.getCanonicalName());
        properties.setProperty("value.deserializer", ConsumptionDeserializer.class.getCanonicalName());
        properties.setProperty("auto.offset.reset", "earliest");
        return properties;
    }

}
