package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.metier.*
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ServiceDeMiseEnCacheDesLots {

    private var mapQuiContientLesLots = ConcurrentHashMap<ClefAccesAuxLots, MaskLot>()

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ServiceDeMiseEnCacheDesLots::class.java)
    }

    fun ajoutOuMiseAJourLots(maskMessage: MaskMessage) {
        if (mapQuiContientLesLots.containsKey(
                ClefAccesAuxLots(
                    maskMessage.entete.idEmetteur,
                    maskMessage.entete.idGreffe,
                    maskMessage.entete.idLot!!
                )
            )
        ) {
            throw IllegalStateException("Le lot a déjà été reçu par rhino ")
        } else {
            mapQuiContientLesLots.put(
                ClefAccesAuxLots(
                    maskMessage.entete.idEmetteur,
                    maskMessage.entete.idGreffe,
                    maskMessage.entete.idLot!!
                ), obtenirMaskLotDepuisMaskMessage(maskMessage)
            )
        }
    }


    fun ajoutProduitsDansLeLot(clefAccesAuxLots: ClefAccesAuxLots, produit: Produit) {
        val maskLot = getMaskLotDepuisMapQuiContientLesLots(clefAccesAuxLots)
        if (maskLot.peutOnDemarrerInterpretation == true) throw java.lang.IllegalStateException("Reception d'un evenement AJOUT_PRODUIT alors que l'interpretation du lot est demarrée")
        maskLot.produits!!.add(obtenirMaskProduitDepuisProduit(produit))

    }

    fun getMaskLotDepuisMapQuiContientLesLots(clefAccesAuxLots: ClefAccesAuxLots): MaskLot {
        LOG.info("recherche du Lot ${clefAccesAuxLots} en memoire")
        val getMaskLotDepuisMap = mapQuiContientLesLots.get(clefAccesAuxLots) ?: throw java.lang.IllegalStateException("Le lot ${clefAccesAuxLots} n'a pas été trouvé en mémoire.")
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
            mutableListOf<MaskProduit>(),
            mutableListOf<MaskAction>(),
            false
        )
    }

    fun obtenirMaskProduitDepuisProduit(produit: Produit): MaskProduit {
        return MaskProduit(
            produit.typeEvenement,
            MaskEvenement(
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
                produit.evenement.listSortieDestinataire
            )

        )

    }

    //TODO renvoye copie de la MAp => à verifier avec Renaud
    fun afficheMapQuiContientLesLots(): Map<ClefAccesAuxLots, MaskLot> = mapQuiContientLesLots.toMap()

}

