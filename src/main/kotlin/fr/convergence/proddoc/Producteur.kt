package fr.convergence.proddoc

import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Producteur {

//    @Outgoing("mygreffeout")
    fun Emission() = ("Alain is the boss")

}