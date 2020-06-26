package fr.convergence.proddoc.reactive

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import fr.convergence.proddoc.model.Parametre
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Parametre {

    companion object {
        private val LOG: Logger = getLogger(Parametre::class.java)
        val cache: Cache<String, String> = Caffeine.newBuilder().maximumSize(1000).build()
    }

    @Incoming("parametre")
    fun ecoute(parametre: Parametre) {
        LOG.info("Réception du paramètre : $parametre");

        val cle = "${parametre.code_domaine}/${parametre.code_sous_domaine}/${parametre.cle}/${parametre.chrono}"
        parametre.valeur?.let { cache.put(cle, it) }

        LOG.info("Mise à jour du cache - key : $cle value : ${parametre.valeur}")

    }
}