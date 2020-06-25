package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.model.Parametre
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Parametre {

    companion object {
        private val LOG: Logger = getLogger(Parametre::class.java)
    }

    @Incoming("parametre")
    fun ecoute(parametre: Parametre) {
        LOG.info("Réception du paramètre : $parametre");
    }
}