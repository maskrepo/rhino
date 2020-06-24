package fr.convergence.proddoc

import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Kafka {

    @Incoming("mygreffe")
    fun consommer(chaine: Parametre) {
        println("chaine.cle : ${chaine.cle} - chaine.valeur : ${chaine.valeur}")
    }
}
