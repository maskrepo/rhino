package fr.convergence.proddoc.parametre

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.slf4j.LoggerFactory
import javax.enterprise.inject.Produces

class CacheParametre2 {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Produces
    fun test(): Topology {

        val streamsBuilder = StreamsBuilder()

        /*val stream = streamsBuilder
            .stream("dbserver1.gtc.p_parametre", Consumed.with(Serdes.String(), Serdes.String()))

        stream.foreach { key, value ->  println("key : $key valeur : $value")}*/



        val table = streamsBuilder.table("dbserver1.gtc.p_parametre", Consumed.with(Serdes.String(), Serdes.String()))

        table.filter { key, value ->  key.contains(":48")}.mapValues { readOnlyKey, value ->  println("value: $value")}.toStream().peek { key, value -> println(value) }

        return streamsBuilder.build()
    }
}