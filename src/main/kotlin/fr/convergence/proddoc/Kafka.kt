package fr.convergence.proddoc

import io.debezium.serde.DebeziumSerdes
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.util.*
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class Kafka {

    //@Incoming("mygreffe")
    fun consommer(chaine: String): String {
        //println("  ${chaine}")

        val serde = DebeziumSerdes.payloadJson(Param::class.java)

        val objectObjectHashMap: HashMap<String, Any> = HashMap()
        objectObjectHashMap["unknown.properties.ignored"] = "true"
        serde.configure(objectObjectHashMap, false)
        val param:Param = serde.deserializer().deserialize("toto", chaine.toByteArray())

        println(param.after.valeur)
        println(param.after.commentaire)

        return chaine.toUpperCase()
    }


}
