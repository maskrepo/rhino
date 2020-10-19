package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.MaskActionDeGeneration
import fr.convergence.proddoc.model.MaskProduit
import fr.convergence.proddoc.model.lib.obj.MaskEntete
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.KbisDemande
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
class ServiceActionsDeGenerations {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ServiceActionsDeGenerations::class.java)
    }

    @Inject
    @field: Channel("kbis_demande")
    val actionDemandeKbisEmitter: Emitter<MaskMessage>? = null

    fun actionDemandeKbis(
        clefAccesAuxLots: ClefAccesAuxLots,
        maskProduit: MaskProduit,
        maskActionDeGeneration: MaskActionDeGeneration
    ) {
        LOG.info("actionDemandeKbis - Lot : ${clefAccesAuxLots} - Produit :  ${maskProduit}")
        val identifiantRegistre = maskProduit.mapObjetMetier!!.getValue("REGISTRE")
        val avecApostille = maskProduit.pourApostille ?: false
        LOG.info("Demande de KBIS pour le registre: ${identifiantRegistre}")
        val kbisDemande = KbisDemande(
            numeroGestion = null,
            identifiantRegistre = identifiantRegistre.toLong(),
            avecApostille = avecApostille,
            avecSceau = false,
            avecSignature = false
        )
        val maskEntete = MaskEntete(
            idUnique = UUID.randomUUID().toString(),
            idLot = clefAccesAuxLots.idLot,
            idEmetteur = clefAccesAuxLots.idEmetteur,
            idGreffe = clefAccesAuxLots.idGreffe,
            typeDemande = "DEMANDE_KBIS",
            dateHeureDemande = LocalDateTime.now(),
            idReference = UUID.randomUUID().toString()
        )
        val maskMessage = MaskMessage(maskEntete, Json.encodeToJsonElement(kbisDemande), null)

        // on ajoute l'action dans la liste des action du lot
        // TODO modifier maskAction mettre directement la reference au MaskProduit
        maskActionDeGeneration.maskMessage = maskMessage


        actionDemandeKbisEmitter?.send(maskMessage)

    }
}