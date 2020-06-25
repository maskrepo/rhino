package fr.convergence.proddoc

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/health")
@ApplicationScoped
class Health {

    @GET
    fun health(): String {
        val streamsBuilder = StreamsBuilder()

        /*val stream = streamsBuilder
            .stream("dbserver1.gtc.p_parametre", Consumed.with(Serdes.String(), Serdes.String()))

        stream.foreach { key, value ->  println("key : $key valeur : $value")}*/

        val table = streamsBuilder.table("dbserver1.gtc.p_parametre", Consumed.with(Serdes.String(), Serdes.String()))
            .toStream().peek { key, value -> println(value) }



return "OK4"
    }



}



