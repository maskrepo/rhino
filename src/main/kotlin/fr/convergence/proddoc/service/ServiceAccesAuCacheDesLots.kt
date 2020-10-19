package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.MaskActionDeGeneration
import fr.convergence.proddoc.model.MaskLot
import fr.convergence.proddoc.model.MaskProduit
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.Lot
import fr.convergence.proddoc.model.metier.Produit
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ServiceAccesAuCacheDesLots {

    private var mapQuiContientLesLots = ConcurrentHashMap<ClefAccesAuxLots, MaskLot>()

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ServiceAccesAuCacheDesLots::class.java)
    }

    fun ajoutOuMiseAJourLots(maskMessage: MaskMessage) {
        val clefAcces =
            ClefAccesAuxLots(maskMessage.entete.idEmetteur, maskMessage.entete.idGreffe, maskMessage.entete.idLot!!)

        val maskLotRecu = obtenirMaskLotDepuisMaskMessage(maskMessage)

        val ValeurExisteDeja = mapQuiContientLesLots.putIfAbsent(clefAcces, maskLotRecu)

        if (ValeurExisteDeja != null) {
            throw IllegalStateException("Le lot a déjà été reçu par rhino : valeur existante ${maskLotRecu} - valeur reçue ${ValeurExisteDeja} ")
        }
    }

    fun ajoutProduitsDansLeLot(clefAccesAuxLots: ClefAccesAuxLots, produit: Produit): MaskProduit {
        val maskLot = getMaskLotDepuisMapQuiContientLesLots(clefAccesAuxLots)
        if (maskLot.peutOnDemarrerInterpretation == true) {
            throw IllegalStateException("Reception d'un evenement AJOUT_PRODUIT alors que l'interpretation du lot ${maskLot} est demarrée")
        }
        if (maskLot.peutOnDemarrerRestitution == true) {
            throw IllegalStateException("Reception d'un evenement AJOUT_PRODUIT alors que la restitution du lot ${maskLot} est demarrée")
        }
        if (maskLot.peutOnDemarrerGeneration == true) {
            throw IllegalStateException("Reception d'un evenement AJOUT_PRODUIT alors que la generation du lot ${maskLot} est demarrée")
        }
        val maskProduit = obtenirMaskProduitDepuisProduitMyGreffe(produit)
        maskLot.produits.add(maskProduit)
        return maskProduit
    }

    fun getMaskLotDepuisMapQuiContientLesLots(clefAccesAuxLots: ClefAccesAuxLots): MaskLot {
        LOG.info("recherche du Lot ${clefAccesAuxLots} en memoire")
        val getMaskLotDepuisMap = mapQuiContientLesLots.get(clefAccesAuxLots)
            ?: throw java.lang.IllegalStateException("Le lot ${clefAccesAuxLots} n'a pas été trouvé en mémoire.")
        return getMaskLotDepuisMap
    }


    fun obtenirMaskLotDepuisMaskMessage(maskMessage: MaskMessage): MaskLot {
        val lotRecu = maskMessage.recupererObjetMetier<Lot>()
        return MaskLot(
            maskMessage.entete.idUnique,
            maskMessage.entete.idLot!!,
            maskMessage.entete.idEmetteur,
            maskMessage.entete.idGreffe,
            maskMessage.entete.typeDemande,
            maskMessage.entete.dateHeureDemande,
            lotRecu.codeUtilisateur,
            mutableListOf<MaskProduit>()
        )
    }

    fun obtenirMaskProduitDepuisProduitMyGreffe(produit: Produit): MaskProduit {
        return MaskProduit(

            produit.evenement.idLot + "_" + UUID.randomUUID().toString(),
            produit.evenement.codeProduit,
            produit.evenement.mapObjetMetier,
            produit.evenement.listeTypesLignesGerees,
            produit.evenement.listeIndicateur,
            produit.evenement.nombreExemplaires,
            produit.evenement.pourApostille,
            produit.evenement.produit,
            produit.evenement.sortieLot,
            produit.evenement.reference,
            produit.evenement.listeIndicateurEntite,
            produit.evenement.sortieEdition,
            produit.evenement.mapSortieDestinataire,
            produit.evenement.description,
            produit.evenement.descriptionKbis,
            produit.evenement.typeDocumentGed,
            produit.evenement.dateTarif,
            produit.evenement.mapDestinataires,
            produit.evenement.listSortieDestinataire,
            mutableListOf<MaskActionDeGeneration>()
        )
    }

    fun afficheMapQuiContientLesLots(): Map<ClefAccesAuxLots, MaskLot> = mapQuiContientLesLots.toMap()

    fun demarrerLot(question: MaskMessage) {
        controleDesDonneesDeObjetMetierLot(question.recupererObjetMetier(), question)
        LOG.info("Mise en mémoire de la demande de type : ${question.entete.typeDemande} - Emetteur : ${question.entete.idEmetteur} - Greffe : ${question.entete.idGreffe} - Lot : ${question.entete.idLot}")
        ajoutOuMiseAJourLots(question)
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
}

