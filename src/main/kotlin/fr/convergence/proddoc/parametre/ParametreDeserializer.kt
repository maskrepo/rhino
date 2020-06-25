package fr.convergence.proddoc.parametre

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

class ParametreDeserializer: ObjectMapperDeserializer<Parametre>(Parametre::class.java)