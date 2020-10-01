package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.lib.obj.MaskEntete
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.ClefAccesAuxLots
import fr.convergence.proddoc.model.metier.KbisDemande
import fr.convergence.proddoc.model.metier.MaskProduit
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import java.time.LocalDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ServiceInterpretation {

    @Inject
    lateinit var serviceDeMiseEnCacheDesLots: ServiceDeMiseEnCacheDesLots

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ServiceInterpretation::class.java)
    }

    fun interpreterLot(clefAccesAuxLots: ClefAccesAuxLots) {

        val maskLot = serviceDeMiseEnCacheDesLots.getMaskLotDepuisMapQuiContientLesLots(clefAccesAuxLots)
        requireNotNull(maskLot)
        requireNotNull(maskLot.produits)

        //TODO voir comment controler que l'utilisateur n'envoie pas N fois les même demandes
        if (maskLot.peutOnDemarrerInterpretation == true) throw IllegalStateException("Reception d'une demande redondante d'interpretation de Lot")
        maskLot.peutOnDemarrerInterpretation = true
        for (maskProduit in maskLot.produits!!) {
            when {
                //TODO ajouter VISU_KBIS et voir le cas ou on à null dans la listeIndicateur
                maskProduit.evenement.codeProduit == "RCS_KBIS" -> {
                    // TODO pour chaque destinataire
                    actionDemandeKbis(clefAccesAuxLots, maskProduit)
                };
                else -> throw IllegalStateException("L'evenement n'a pas pu être interpreté correctement")
            }
            // examiner tous les produits de la liste
            // alimenter la liste d action
            //generer une numero d action
        }
    }

    @Inject
    @field: Channel("kbis_demande")
    val actionDemandeKbisEmitter: Emitter<MaskMessage>? = null

    fun actionDemandeKbis(clefAccesAuxLots: ClefAccesAuxLots, maskProduit: MaskProduit) {
        ServiceInterpretation.LOG.info("actionDemandeKbis : ${clefAccesAuxLots} ")
        val numeroGestion = maskProduit.evenement.mapObjetMetier!!.getValue("REGISTRE")
            ?: throw IllegalStateException("tentative de déclancher une actionDemandeDeKbis avec parametre non renseigne")
        val avecApostille = maskProduit.evenement.pourApostille ?: false
        val kbisDemande = KbisDemande(numeroGestion = numeroGestion, avecApostille = avecApostille, avecSceau = false, avecSignature = false)
        val maskEntete = MaskEntete(idUnique = UUID.randomUUID().toString(),idLot = clefAccesAuxLots.idLot, idEmetteur = clefAccesAuxLots.idEmetteur, idGreffe = clefAccesAuxLots.idGreffe,typeDemande = "DEMANDE_KBIS",dateHeureDemande = LocalDateTime.now() )
        val maskMessage = MaskMessage(maskEntete, Json.encodeToJsonElement(kbisDemande), null)

        actionDemandeKbisEmitter?.send(maskMessage)

    }

}

//TODO eventuellement surcharge
//TODO eventuellement restituer