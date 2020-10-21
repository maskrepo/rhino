package fr.convergence.proddoc.helper

import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.ClefAccesAuxLots
import fr.convergence.proddoc.model.MaskActionDeGeneration
import fr.convergence.proddoc.model.MaskLot
import fr.convergence.proddoc.model.lib.obj.MaskMessage
import java.lang.IllegalStateException

object HelperRhino {

    fun obtenirClefAccesLot(maskMessage: MaskMessage) : ClefAccesAuxLots {
        return ClefAccesAuxLots(idEmetteur = maskMessage.entete.idEmetteur,idGreffe = maskMessage.entete.idGreffe,idLot = maskMessage.entete.idLot!!)
    }


}