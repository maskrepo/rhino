package fr.convergence.proddoc.helper

import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.MaskAction
import fr.convergence.proddoc.model.MaskLot
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import java.lang.IllegalStateException

object HelperRhino {

    fun obtenirClefAccesLot(maskMessage: MaskMessage) : ClefAccesAuxLots {
        return ClefAccesAuxLots(idEmetteur = maskMessage.entete.idEmetteur,idGreffe = maskMessage.entete.idGreffe,idLot = maskMessage.entete.idLot!!)
    }

    fun obtenirMaskActionPourUneIdReference(_idReference : String , maskLot: MaskLot ) : MaskAction {
        val listeActions = maskLot.actions.filter { it.maskMessage.entete.idReference == _idReference }
        if(listeActions.size != 1) {
            throw IllegalStateException("Tentative de mise à jour de l'état d'une action. Or, on ne l'a retrouve pas une et une seule fois dans la liste des actions.")
        }

        return listeActions.get(0)
    }

    fun majEtatActionPourUneIdReference( idReference : String, maskLot: MaskLot, _actionEtat: ActionEtat) {
        val maskAction =  obtenirMaskActionPourUneIdReference(idReference, maskLot)
        if (maskAction.actionEtat == ActionEtat.ACTION_NON_REALISEE) {
            maskAction.actionEtat = _actionEtat
        }
        else
        {
            throw IllegalStateException("Tentative de mettre à jour l'Etat d'une pour laquelle on a déjà reçu une réponse")
        }
    }

    fun obtenirListeDesActionPourLeProduitCorrespondantReponseRecue(idReference : String, maskLot: MaskLot) : List<MaskAction> {
        // Pour une Reponse on recherche l'Evenement correspondant ( un evenement wrape un produit )
        val _idEvenementDuquelDependCetteAction = obtenirMaskActionPourUneIdReference( idReference,  maskLot ).idEvenementDuquelDependCetteAction
        // On constitue la list des actions qui correspondent à cet evenenement ( wrape un produit )
        val listeActions= maskLot.actions.filter { it.idEvenementDuquelDependCetteAction == _idEvenementDuquelDependCetteAction }
        return listeActions
    }

}