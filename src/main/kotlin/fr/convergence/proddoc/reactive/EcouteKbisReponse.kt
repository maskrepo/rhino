package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.helper.HelperRhino
import fr.convergence.proddoc.helper.HelperRhino.obtenirListeDesActionPourLeProduitCorrespondantReponseRecue
import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.KbisDemande
import fr.convergence.proddoc.model.metier.Lot
import fr.convergence.proddoc.service.ServiceAccesAuCacheDesLots
import fr.convergence.proddoc.util.maskIOHandler
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EcouteKbisReponse {

    @Inject
    lateinit var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(EcouteDemarrerLotDemande::class.java)
    }

    @Incoming("kbis_reponse")
    fun receptionKbisReponse(question: MaskMessage): MaskMessage = maskIOHandler(question) {
        val maskLot = serviceAccesAuCacheDesLots.getMaskLotDepuisMapQuiContientLesLots(HelperRhino.obtenirClefAccesLot(question))
        when {
            question.isReponse() && question.reponse!!.estReponseOk  == true -> {
                //Une reponse ok implique que l on doit mettre a jour l etat de l'action dans la liste des actions
                HelperRhino.majEtatActionPourUneIdReference(question.entete.idReference,maskLot, ActionEtat.ACTION_REALISEE_AVEC_SUCCES)
            }
            question.isReponse() && question.reponse!!.estReponseOk == false -> {
                //Une reponse ko implique que l on doit mettre a jour l etat de l'action dans la liste des actions
                HelperRhino.majEtatActionPourUneIdReference(question.entete.idReference,maskLot, ActionEtat.ACTION_REALISEE_EN_ECHEC)
            }
            else -> throw IllegalStateException("message inconnu reçu dans le topic kbis_reponse")
        }

            

        val listeAction = obtenirListeDesActionPourLeProduitCorrespondantReponseRecue(question.entete.idReference, maskLot)
        // Est ce quil s agit de la derniere reponse attendue
        val listeActionsNonRealisees = listeAction.filter {it.actionEtat == ActionEtat.ACTION_NON_REALISEE}
        // Est ce qu'au moins une action est en echec
        val listeActionsEnEchec = listeAction.filter {it.actionEtat == ActionEtat.ACTION_REALISEE_EN_ECHEC}

        if ( listeActionsNonRealisees.size == 0 && listeActionsEnEchec.size == 0) {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} ont été réalisées avec succés")
            LOG.info("Déclenchement restitution")
            //TODO declenche restitution
            LOG.info("Déclenchement prevenir myGreffe produit OK")
            //TODO prevenir myGReffe produit genere
        }
        if ( listeActionsNonRealisees.size == 0 && listeActionsEnEchec.size != 0) {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} n'ont pas été réalisées avec succés")
            LOG.info("Ne pas déclencher restitution")
            LOG.info("Déclenchement prevenir myGreffe produit KO")

        }
        else
        {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} n'ont pas été réalisées")
            LOG.info("Aucun déclenchement à faire")
        }
    }

}