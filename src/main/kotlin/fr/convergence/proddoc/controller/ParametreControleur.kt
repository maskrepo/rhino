package fr.convergence.proddoc.controller

import fr.convergence.proddoc.reactive.Parametre
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam

@Path("/parametre")
class ParametreControleur {

    @GET
    fun param(@QueryParam("cle") cle:String): String? {
        return Parametre.cache.getIfPresent(cle)
    }
}