package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.ActionEtat
import fr.convergence.proddoc.model.ClefAccesAuxLots
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ServiceGenerationLot(
    @Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots,
    @Inject var serviceActionsDeGenerations: ServiceActionsDeGenerations
) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ServiceGenerationLot::class.java)
    }

    fun genererLot(clefAccesAuxLots: ClefAccesAuxLots) {

        val maskLot = serviceAccesAuCacheDesLots.getMaskLotDepuisMapQuiContientLesLots(clefAccesAuxLots)
        requireNotNull(maskLot)
        requireNotNull(maskLot.produits)

        if (maskLot.peutOnDemarrerGeneration == true) throw IllegalStateException("Reception d'une demande redondante d'interpretation de Lot")
        maskLot.peutOnDemarrerGeneration = true

        for (maskProduit in maskLot.produits) {
            for (maskActionDeGeneration in maskProduit.actionsDeGenerations) {
                when {
                    maskActionDeGeneration.actionDeGeneration == "actionDemandeKbis" -> {
                        serviceActionsDeGenerations.actionDemandeKbis(clefAccesAuxLots, maskProduit, maskActionDeGeneration)
                    };
                    else -> throw IllegalStateException("L'evenement n'a pas pu être interpreté correctement")
                }
                maskActionDeGeneration.actionEtat = ActionEtat.ACTION_GENEREE
            }
        }
    }


}

