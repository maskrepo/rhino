package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.MaskActionDeGeneration
import fr.convergence.proddoc.model.MaskActionDeRestitution
import fr.convergence.proddoc.model.MaskProduit
import javax.inject.Inject

class ServiceInterpretationLot(@Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots) {

    fun interpreterProduit(maskProduit: MaskProduit) {

        when {
            maskProduit.codeProduit == "RCS_KBIS" -> {
                maskProduit.actionsDeGenerations.add(
                    MaskActionDeGeneration(
                        null,
                        "actionDemandeKbis",
                        mutableListOf<MaskActionDeRestitution>(),
                        ActionEtat.ACTION_NON_REALISEE
                    )
                )
            };
            else -> throw IllegalStateException("L'evenement n'a pas pu être interpreté correctement")
        }

    }
}


//TODO ajouter VISU_KBIS et voir le cas ou on à null dans la listeIndicateur
// TODO pour chaque destinataire