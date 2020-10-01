package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.Lot
import fr.convergence.proddoc.service.ServiceDeMiseEnCacheDesLots
import fr.convergence.proddoc.util.maskIOHandler
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EcouteDemarrerLotDemande {

    @Inject
    lateinit var serviceDeMiseEnCacheDesLots: ServiceDeMiseEnCacheDesLots

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(EcouteDemarrerLotDemande::class.java)
    }

    @Incoming("demarrer_lot_demande")
    @Outgoing("demarrer_lot_resultat")
    fun demarrerLot(question: MaskMessage): MaskMessage = maskIOHandler(question) {
        controleDesDonneesDeEntete(question)
        controleDesDonneesDeObjetMetierLot(question.recupererObjetMetier<Lot>(), question)
        EcouteDemarrerLotDemande.LOG.info("Réception d'une demande de type : ${question.entete.typeDemande} - indentifiant lot : ${question.entete.idLot} - details : ${question}")

        EcouteDemarrerLotDemande.LOG.info("Mise en mémoire de la demande de type : ${question.entete.typeDemande} - Emetteur : ${question.entete.idEmetteur} - Greffe : ${question.entete.idGreffe} - Lot : ${question.entete.idLot}")
        serviceDeMiseEnCacheDesLots.ajoutOuMiseAJourLots(question)
        serviceDeMiseEnCacheDesLots.afficheMapQuiContientLesLots()
        question
    }

    private fun controleDesDonneesDeObjetMetierLot(
        lot: Lot,
        question: MaskMessage
    ) {
        requireNotNull(lot.identifiant)
        requireNotNull(lot.dateDemande)
        requireNotNull(lot.codeUtilisateur)
        require(lot.identifiant == question.entete.idLot) { "L'identifiant du lot dans l'entete et celui dans " }
    }

    private fun controleDesDonneesDeEntete(question: MaskMessage) {
        requireNotNull(question.entete)
        requireNotNull(question.entete.idLot) { "idLot est obligatoire, or il est à null dans le message de démarrage de lot" }
        requireNotNull(question.entete.typeDemande) { "Le type de demande n'a pas été positionné" }
        require(question.entete.typeDemande == "lot")
    }


}