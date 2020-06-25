package fr.convergence.proddoc.parametre

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces


@ApplicationScoped
class CacheParametreStreamsApi {

    private val logger = LoggerFactory.getLogger(javaClass)

    //@Produces
    fun test(): Topology {

        val streamsBuilder = StreamsBuilder()

        val avroSerde = GenericAvroSerde().apply {
            configure(mapOf(Pair("schema.registry.url", "http://localhost:8080")), false)
        }

        val parametrageAvroStream: KStream<String, GenericRecord> = streamsBuilder
            .stream("parametrage", Consumed.with(Serdes.String(), avroSerde))

        val parametrageStream: KStream<String, Parametre> = parametrageAvroStream.mapValues { parametreAvro ->
            val parametre = Parametre(
                nom = parametreAvro["nom"].toString(),
                valeur = parametreAvro["valeur"].toString()
            )
            logger.debug("Parametre: $parametre")
            parametre
        }

        return streamsBuilder.build()
    }

}