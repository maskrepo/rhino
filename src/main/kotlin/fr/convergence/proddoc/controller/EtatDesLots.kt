package fr.convergence.proddoc.controller

import fr.convergence.proddoc.service.ServiceAccesAuCacheDesLots
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/etatDesLots")
@ApplicationScoped
class EtatDesLots(@Inject var serviceAccesAuCacheDesLots: ServiceAccesAuCacheDesLots) {

    @GET
    fun etatDesLots() : String = serviceAccesAuCacheDesLots.afficheMapQuiContientLesLots().toString()

}