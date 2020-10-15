package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.helper.HelperRhino
import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.MaskProduit
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.KbisRetour
import fr.convergence.proddoc.service.ServiceAccesAuCacheDesLots
import fr.convergence.proddoc.service.ServiceRestituerParImpression
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EcouteKbisReponse(
    @Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots,
    @Inject var serviceRestituerParImpression: ServiceRestituerParImpression
) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(EcouteKbisReponse::class.java)
    }

    @Incoming("kbis_reponse")
    fun receptionKbisReponse(question: MaskMessage) {
        val maskLot =
            serviceAccesAuCacheDesLots.getMaskLotDepuisMapQuiContientLesLots(HelperRhino.obtenirClefAccesLot(question))
        val kbisRetour = question.recupererObjetMetier<KbisRetour>()
        var produitMaj: MaskProduit?
        when {
            question.isReponse() && question.reponse!!.estReponseOk == true -> {
                //Une reponse ok implique que l on doit mettre a jour l etat de l'action dans la liste des actions
                produitMaj = maskLot.majEtatAction(question.entete.idReference, ActionEtat.ACTION_REALISEE_AVEC_SUCCES)
            }
            question.isReponse() && question.reponse!!.estReponseOk == false -> {
                //Une reponse ko implique que l on doit mettre a jour l etat de l'action dans la liste des actions
                produitMaj = maskLot.majEtatAction(question.entete.idReference, ActionEtat.ACTION_REALISEE_EN_ECHEC)
            }
            else -> throw IllegalStateException("message inconnu reçu dans le topic kbis_reponse")
        }

        val listeAction = produitMaj!!.actionsDeGenerations
        val listeActionsNonRealisees = listeAction.filter { it.actionEtat == ActionEtat.ACTION_NON_REALISEE }
        val listeActionsEnEchec = listeAction.filter { it.actionEtat == ActionEtat.ACTION_REALISEE_EN_ECHEC }

        if (listeActionsNonRealisees.size == 0 && listeActionsEnEchec.size == 0) {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} ont été réalisées avec succés")
            LOG.info("Déclenchement restitution")
            serviceRestituerParImpression.impressionDemande(
                kbisRetour.messageRetour,
                ClefAccesAuxLots(question.entete.idEmetteur, question.entete.idGreffe, question.entete.idLot!!)
            )
            LOG.info("Déclenchement prevenir myGreffe produit OK")
        }

        if (listeActionsNonRealisees.size == 0 && listeActionsEnEchec.size != 0) {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} n'ont pas été réalisées avec succés")
            LOG.info("Ne pas déclencher restitution")
            LOG.info("Déclenchement prevenir myGreffe produit KO")
        } else {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} n'ont pas été réalisées")
            LOG.info("Aucun déclenchement à faire")
        }
    }


}