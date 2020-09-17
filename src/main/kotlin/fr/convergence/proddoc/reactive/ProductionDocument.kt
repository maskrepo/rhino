package fr.convergence.proddoc.reactive

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class ProductionDocument {

    companion object {
        private val LOG: Logger = getLogger(ProductionDocument::class.java)
    }

    @Incoming("demarrer_lot")
    fun demarrerLot(lot: String) {
        LOG.info("Réception d'une demande de nouveau lot : $lot");
    }
}