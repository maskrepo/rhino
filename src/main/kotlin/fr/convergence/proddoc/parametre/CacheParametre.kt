package fr.convergence.proddoc.parametre

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CacheParametre {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val cache: Cache<String, String> = Caffeine.newBuilder()
        .maximumSize(1000)
        .build()


    private fun majParametre(parametre: Parametre) {

        logger.info("Objet lu dans Kafka : $parametre")
        cache.put(parametre.nom, parametre.valeur)
    }

    @Incoming("parametrage")
    private fun majParametre(parametre: String) {

        logger.info("Objet lu dans Kafka : $parametre")
    }

    fun lireParametre(nomParametre: String): String? {

        logger.info("Lecture du parametre $nomParametre")
        return cache.getIfPresent(nomParametre)
    }

}