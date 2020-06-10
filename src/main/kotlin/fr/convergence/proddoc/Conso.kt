package fr.convergence.proddoc

import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Conso {

    @Incoming("mygreffe")
    fun getMessage(msg : String) = println(msg)

}