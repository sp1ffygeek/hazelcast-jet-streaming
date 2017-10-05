package com.hazelcast.jet.demo;

import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.core.DAG;
import com.hazelcast.jet.core.Partitioner;
import com.hazelcast.jet.core.TimestampKind;
import com.hazelcast.jet.core.Vertex;
import com.hazelcast.jet.core.WindowDefinition;
import com.hazelcast.jet.core.processor.DiagnosticProcessors;
import com.hazelcast.jet.demo.data.Consumption;

import static com.hazelcast.jet.core.Edge.from;
import static com.hazelcast.jet.core.WatermarkEmissionPolicy.emitByFrame;
import static com.hazelcast.jet.core.WatermarkPolicies.withFixedLag;
import static com.hazelcast.jet.core.processor.KafkaProcessors.streamKafka;
import static com.hazelcast.jet.core.processor.Processors.aggregateToSlidingWindow;
import static com.hazelcast.jet.core.processor.Processors.insertWatermarks;
import static com.hazelcast.jet.core.processor.Processors.map;
import static com.hazelcast.jet.demo.util.Config.getProperties;
import static com.hazelcast.jet.function.DistributedFunctions.entryValue;

/**
 * date: 9/29/17
 * author: emindemirci
 */
public class AvgElectricity {

    public static void main(String[] args) {

        JetInstance jetInstance = Jet.newJetInstance();

        DAG dag = new DAG();
        Vertex kafkaReader = dag.newVertex("kafka-reader", streamKafka(getProperties(), "electricity-consumption"));

        Vertex extractConsumption = dag.newVertex("extract-consumption", map(entryValue()));

        WindowDefinition windowDefinition = WindowDefinition.slidingWindowDef(10000, 1000);

        Vertex insertWm = dag.newVertex("insertWm",
                insertWatermarks(Consumption::getTimestamp, withFixedLag(10000),
                        emitByFrame(windowDefinition))
        );

        Vertex avgConsumption = dag.newVertex("avgConsumption",
                DiagnosticProcessors.peekInput(aggregateToSlidingWindow(
                        Consumption::getApplianceName,
                        Consumption::getTimestamp, TimestampKind.EVENT,
                        windowDefinition,
                        AggregateOperations.averagingLong(Consumption::getAmount))));

        Vertex printer = dag.newVertex("printer", DiagnosticProcessors.writeLogger());

        dag.edge(from(kafkaReader).to(extractConsumption));
        dag.edge(from(extractConsumption).to(insertWm).isolated());
        dag.edge(from(insertWm).to(avgConsumption)
                               .partitioned(Consumption::getApplianceName, Partitioner.HASH_CODE)
                               .distributed()
        );
        dag.edge(from(avgConsumption).to(printer).isolated());

        jetInstance.newJob(dag).join();


    }


}
