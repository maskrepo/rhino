package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.KbisDemande
import fr.convergence.proddoc.util.maskIOHandler
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped
import kotlin.random.Random

@ApplicationScoped
class ProductionDocument {

    companion object {
        private val LOG: Logger = getLogger(ProductionDocument::class.java)
    }

    @Incoming("demarrer_lot_resultat")
    fun resultatDemarrerLot(lot: MaskMessage) {
        LOG.info("Retour demande de nouveau lot, Ok = ${lot.reponse?.estReponseOk}")
    }


    @Incoming("demarrer_lot")
    @Outgoing("demarrer_lot_resultat_sortie")
    fun demarrerLot(question: MaskMessage): MaskMessage = maskIOHandler(question) {
        LOG.info("Réception d'une demande de nouveau lot : $question")
        if (Random.nextInt(0, 3) == 1) {
            throw IllegalStateException("oups")
        }
        KbisDemande("2012B00012", false, true, true)
    }
}