package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.helper.HelperRhino
import fr.convergence.proddoc.helper.HelperRhino.obtenirListeDesActionPourLeProduitCorrespondantReponseRecue
import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.lib.obj.MaskEntete
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.DemandeImpression
import fr.convergence.proddoc.model.metier.KbisDemande
import fr.convergence.proddoc.model.metier.KbisRetour
import fr.convergence.proddoc.model.metier.Lot
import fr.convergence.proddoc.service.ServiceAccesAuCacheDesLots
import fr.convergence.proddoc.service.ServiceInterpretation
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.time.LocalDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EcouteKbisReponse(@Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(EcouteDemarrerLotDemande::class.java)
    }

    @Incoming("kbis_reponse")
    fun receptionKbisReponse(question: MaskMessage) {
        val maskLot = serviceAccesAuCacheDesLots.getMaskLotDepuisMapQuiContientLesLots(HelperRhino.obtenirClefAccesLot(question))
        val kbisRetour = question.recupererObjetMetier<KbisRetour>()
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
        val listeActionsNonRealisees = listeAction.filter {it.actionEtat == ActionEtat.ACTION_NON_REALISEE}
        val listeActionsEnEchec = listeAction.filter {it.actionEtat == ActionEtat.ACTION_REALISEE_EN_ECHEC}

        if ( listeActionsNonRealisees.size == 0 && listeActionsEnEchec.size == 0) {
            LOG.info("L'ensemble des actions pour le produit ${question.entete} ont été réalisées avec succés")
            LOG.info("Déclenchement restitution")
            ImpressionDemande(kbisRetour.messageRetour,ClefAccesAuxLots(question.entete.idEmetteur,question.entete.idGreffe,question.entete.idLot!!))
            LOG.info("Déclenchement prevenir myGreffe produit OK")
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

    @Inject
    @field: Channel("impression_demande")
    val actionImpressionDemandeEmitter: Emitter<MaskMessage>? = null

    fun impressionDemande(urlFichierAImprimer : String, clefAccesAuxLots : ClefAccesAuxLots){

        LOG.info("actionImpressionDemande : ${clefAccesAuxLots} ")
        val impressionDemande = DemandeImpression(IDsortieDocument = "" ,urlFichierAImprimer = urlFichierAImprimer ,rectoVerso = true,nomImprimante = "",nomBacEntree = "",nbExemplaires = 1 )

        val maskEntete = MaskEntete(
            idUnique = UUID.randomUUID().toString(),
            idLot = clefAccesAuxLots.idLot,
            idEmetteur = clefAccesAuxLots.idEmetteur,
            idGreffe = clefAccesAuxLots.idGreffe,
            typeDemande = "DEMANDE_IMPRESSION",
            dateHeureDemande = LocalDateTime.now(),
            idReference = UUID.randomUUID().toString()
        )
        val maskMessage = MaskMessage(maskEntete, Json.encodeToJsonElement(impressionDemande), null)

        actionImpressionDemandeEmitter?.send(maskMessage)
    }



}