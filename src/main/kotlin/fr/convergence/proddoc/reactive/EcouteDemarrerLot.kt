package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.Lot
import fr.convergence.proddoc.service.ServiceAccesAuCacheDesLots
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EcouteDemarrerLot(@Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(EcouteDemarrerLot::class.java)
    }

    @Incoming("demarrer_lot_demande")
    @Outgoing("demarrer_lot_resultat")
    fun demarrerLot(question: MaskMessage): MaskMessage {
        LOG.info("Réception d'une demande de type : ${question.entete.typeDemande} - indentifiant lot : ${question.entete.idLot} - details : ${question}")
        serviceAccesAuCacheDesLots.demarrerLot(question)
        val lot : Lot = question.recupererObjetMetier();
        return MaskMessage.reponseOk(lot, question, question.entete.idReference)
    }
}