package fr.convergence.proddoc

import io.debezium.serde.DebeziumSerdes
import org.apache.kafka.common.serialization.Deserializer
import java.util.HashMap

class ParametreDeserializer : Deserializer<Parametre?> {

    override fun deserialize(topic: String?, data: ByteArray?): Parametre? {

        val serde = DebeziumSerdes.payloadJson(PayLoadParametre::class.java)
        val objectObjectHashMap = HashMap<String, Any>()
        objectObjectHashMap["unknown.properties.ignored"] = "true"
        serde.configure(objectObjectHashMap, false)
        val deserialize = serde.deserializer().deserialize(topic, data)
        return deserialize.after
    }
}