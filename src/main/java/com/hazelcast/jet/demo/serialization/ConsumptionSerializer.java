package com.hazelcast.jet.demo.serialization;

import com.hazelcast.jet.demo.data.Consumption;
import java.nio.ByteBuffer;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 * date: 9/29/17
 * author: emindemirci
 */
public class ConsumptionSerializer implements Serializer<Consumption> {
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public byte[] serialize(String topic, Consumption data) {
        ByteBuffer bb = ByteBuffer.allocate(512);
        byte[] applianceName = data.getApplianceName().getBytes();
        bb.putInt(applianceName.length);
        bb.put(applianceName);
        bb.putInt(data.getAmount());
        bb.putLong(data.getTimestamp());
        return bb.array();
    }

    public void close() {
    }
}
