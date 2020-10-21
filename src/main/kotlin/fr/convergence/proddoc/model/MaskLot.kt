package fr.convergence.proddoc.model

import java.time.LocalDateTime

class MaskLot(
    val idUniqueDeLaDemandeALOrigineDuLot: String,
    val idLot: String,
    val idEmetteur: String?,
    val idGreffe: String,
    val typeDemande: String?,
    val dateDeReceptionDuLot: LocalDateTime,
    val codeUtilisateur: String,
    val produits: MutableList<MaskProduit>
) {

    var peutOnDemarrerInterpretation: Boolean = false
    var peutOnDemarrerRestitution: Boolean = false
    var peutOnDemarrerGeneration: Boolean = false


    fun majEtatAction(idReferenceActionGenerationRecue : String, etat: ActionEtat) : MaskProduit? {
        for (produit in produits) {
            for (actionsDeGenerations in produit.actionsDeGenerations){
                if (actionsDeGenerations.maskMessage!!.entete.idReference == idReferenceActionGenerationRecue){
                    actionsDeGenerations.actionEtat == etat
                }
                return produit
            }
        }
        return null
    }
}