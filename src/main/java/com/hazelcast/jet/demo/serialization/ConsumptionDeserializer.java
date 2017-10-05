package com.hazelcast.jet.demo.serialization;

import com.hazelcast.jet.demo.data.Consumption;
import java.nio.ByteBuffer;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * date: 9/29/17
 * author: emindemirci
 */
public class ConsumptionDeserializer implements Deserializer<Consumption> {
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public Consumption deserialize(String topic, byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        int applianceNameSize = bb.getInt();
        byte[] applianceNameBytes = new byte[applianceNameSize];
        Consumption consumption = new Consumption();
        bb.get(applianceNameBytes);
        consumption.setApplianceName(new String(applianceNameBytes));
        consumption.setAmount(bb.getInt());
        consumption.setTimestamp(bb.getLong());
        return consumption;
    }

    public void close() {

    }
}
