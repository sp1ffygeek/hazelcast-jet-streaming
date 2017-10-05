package com.hazelcast.jet.demo;

import com.hazelcast.jet.demo.data.Consumption;
import com.hazelcast.jet.demo.util.Config;
import java.util.Random;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * date: 9/29/17
 * author: emindemirci
 */
public class ConsumptionGenerator {

    static String[] appliances = new String[]{"Refrigerator", "TV", "Stove", "Dishwasher", "Washing Machine"};

    public static void main(String[] args) throws InterruptedException {
        KafkaProducer<Long, Consumption> producer = new KafkaProducer<>(Config.getProperties());

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String appliance = appliances[random.nextInt(appliances.length)];
            int amount = (int) (random.nextDouble() * 1000) + 200;
            ProducerRecord<Long, Consumption> consumption = new ProducerRecord<>("electricity-consumption", new Consumption(appliance, amount, System.currentTimeMillis()));
            producer.send(consumption);
            Thread.sleep((long) (50 * random.nextDouble() + 20));
        }
        producer.close();
    }
}
