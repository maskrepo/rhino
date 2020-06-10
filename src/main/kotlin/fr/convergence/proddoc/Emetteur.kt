package fr.convergence.proddoc

import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/emission")
@ApplicationScoped
class Emetteur {

/*    @Outgoing("mygreffeout")
    @GET
    fun Emission() = "hello from alain"
*/
}