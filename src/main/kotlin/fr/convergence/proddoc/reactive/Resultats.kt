package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.libs.service.ParametreCache
import fr.convergence.proddoc.libs.model.Parametre
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
public class Resultats(@Inject var cache: ParametreCache) {

    companion object {
        private val LOG: Logger = getLogger(Parametre::class.java)
    }

    @Incoming("kbis_fini")
    fun ecoute(lekbis :Int) {
        LOG.info("Réception du résultat : $lekbis");
    }
}