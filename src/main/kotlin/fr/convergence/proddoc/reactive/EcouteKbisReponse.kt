package fr.convergence.proddoc.reactive

import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EcouteKbisReponse {

    @Incoming("kbis_reponse")
    fun receptionKbisReponse(){
        //TODO reponse ok
        //TODO reponse ko
        // est ce quil s agit de la derniere reponse attendue

    }

}