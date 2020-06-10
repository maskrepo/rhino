package fr.convergence.proddoc

import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/health")
@ApplicationScoped
class Health {

    @GET
    fun health(): String = "OK"

}