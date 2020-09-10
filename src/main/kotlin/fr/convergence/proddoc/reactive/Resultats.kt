package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.libs.service.ParametreCache
import fr.convergence.proddoc.libs.model.Parametre
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.client.WebClient
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
public class Resultats(@Inject var cache: ParametreCache) {

    companion object {
        private val LOG: Logger = getLogger(Parametre::class.java)
    }

    @Incoming("kbis_fini")
    @Outgoing("kbis_status")
    fun ecoute(urlKbis :String) : String{
        LOG.info("Réception du résultat : $urlKbis")
        val client = WebClient.create(Vertx.vertx())
        //@TODO appliquer recommandation un client unique pour toute l'appli au lieu de l'instancier à chaque appel de la fonction
        val pdf = client
            .getAbs(urlKbis)
            .rxSend()
            .map { it.bodyAsBuffer().bytes }
            .blockingGet()
        return if (pdf.isNotEmpty()) {
            LOG.info("KBIS_OK")
            ("KBIS_OK")
        } else {
            LOG.info("KBIS_KO")
            ("KBIS_KO")
        }
    }
}