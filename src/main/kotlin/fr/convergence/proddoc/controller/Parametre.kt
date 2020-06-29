package fr.convergence.proddoc.controller

import fr.convergence.proddoc.service.ParametreCache
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/parametre")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
class Parametre @Inject constructor(val cache: ParametreCache) {

    @GET
    @Path("/all")
    fun list(): List<fr.convergence.proddoc.model.Parametre> = cache.getAll().sortedByDescending { it.timestamp }
}