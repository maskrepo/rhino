package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.Produit
import fr.convergence.proddoc.service.ServiceAccesAuCacheDesLots
import fr.convergence.proddoc.service.ServiceGenerationLot
import fr.convergence.proddoc.service.ServiceInterpretationLot
import io.vertx.core.logging.LoggerFactory.*
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EcouteProduitsDemande(
    @Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots,
    @Inject var serviceGenerationLot: ServiceGenerationLot,
    @Inject var serviceInterpretationLot: ServiceInterpretationLot
) {

    companion object {
        private val LOG = getLogger(EcouteProduitsDemande::class.java)
    }

    @Incoming("produits_demande")
    @Outgoing("produits_reponse")
    fun receptionProduits(question: MaskMessage): MaskMessage {

        LOG.info("Réception d'une demande de type : ${question.entete.typeDemande} - indentifiant lot : ${question.entete.idLot} - details : ${question}")
        controleDesDonneesDeEntete(question)
        val produit = question.recupererObjetMetier<Produit>()
        controleDonneesDeObjetMetierProduit(produit)

        val clefAccesAuxLots =
            ClefAccesAuxLots(question.entete.idEmetteur, question.entete.idGreffe, question.entete.idLot!!)

        when (question.entete.typeDemande) {
            "DEMARRER LOT" -> {
                serviceAccesAuCacheDesLots.demarrerLot(question)
            };

            "AJOUT PRODUIT" -> {
                val maskProduit = serviceAccesAuCacheDesLots.ajoutProduitsDansLeLot(clefAccesAuxLots, produit)
                serviceInterpretationLot.interpreterProduit(maskProduit)
            };

            "INTERPRETATION LOT" -> {
                question
            };
            "GENERER LOT" -> {
                serviceGenerationLot.genererLot(clefAccesAuxLots)
            };
            else -> throw IllegalStateException("reception d'un produit contenant un typeEvenement inconnu")
        }

        return MaskMessage.reponseOk("Ok", question, question.entete.idReference)
    }

    private fun controleDesDonneesDeEntete(question: MaskMessage) {
        requireNotNull(question.entete)
        requireNotNull(question.entete.idLot) { "idLot est obligatoire, or il est à null dans le message de démarrage de lot" }
        requireNotNull(question.entete.typeDemande) { "Le type de demande n'a pas été positionné" }
        requireNotNull(question.entete.idGreffe) { "L'idGreffe n'a pas été positionné" }
        requireNotNull(question.entete.idEmetteur) { "L'idEmetteur n'a pas été positionné" }
    }

    private fun controleDonneesDeObjetMetierProduit(produit: Produit) {
        requireNotNull(produit.typeEvenement)
    }

}