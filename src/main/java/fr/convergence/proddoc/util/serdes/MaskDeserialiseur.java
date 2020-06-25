package fr.convergence.proddoc.util.serdes;

import fr.convergence.proddoc.model.MaskTable;
import fr.convergence.proddoc.model.Parametre;
import io.debezium.serde.DebeziumSerdes;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.reflections.Reflections;

import java.util.HashMap;

public class MaskDeserialiseur implements Deserializer {

    @Override
    public Object deserialize(String topic, byte[] data) {
        Serde<PayloadDebezium> serde = DebeziumSerdes.payloadJson(PayloadDebezium.class);
        HashMap objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("unknown.properties.ignored", "true");
        serde.configure(objectObjectHashMap, false);
        PayloadDebezium payloadDebezium = serde.deserializer().deserialize(topic, data);
        System.out.println(payloadDebezium);

        String table = payloadDebezium.getSource().getTable();
        Reflections reflections = new Reflections("fr.convergence.proddoc.model");
        Class<?> classDeserialization = reflections.getTypesAnnotatedWith(MaskTable.class).stream().filter(x -> x.getAnnotation(MaskTable.class).value().equals(table)).findFirst().get();

        Serde serde2 = DebeziumSerdes.payloadJson(classDeserialization);
        HashMap objectObjectHashMap2 = new HashMap<>();
        objectObjectHashMap2.put("unknown.properties.ignored", "true");
        objectObjectHashMap2.put("from.field", "after");
        serde2.configure(objectObjectHashMap2, false);
        return serde2.deserializer().deserialize(topic, data);
    }
}