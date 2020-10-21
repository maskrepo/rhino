package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.lib.obj.MaskEntete
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.DemandeImpression
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
class ServiceRestituerParImpression {

    companion object {
        private val LOG = LoggerFactory.getLogger(ServiceRestituerParImpression::class.java)
    }

    @Inject
    @field: Channel("impression_demande")
    val actionImpressionDemandeEmitter: Emitter<MaskMessage>? = null

    fun impressionDemande(urlFichierAImprimer : String, clefAccesAuxLots : ClefAccesAuxLots){

        LOG.info("actionImpressionDemande : ${clefAccesAuxLots} ")
        val impressionDemande = DemandeImpression(iDsortieDocument = null ,urlFichierAImprimer = urlFichierAImprimer ,rectoVerso = true, nomImprimante = "", nomBacEntree = "", nbExemplaires = 1)

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