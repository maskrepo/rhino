package fr.convergence.proddoc

import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import kotlinx.serialization.json.*


@ApplicationScoped
class Conso {

    @Incoming("mygreffe")
    fun getMessage(msg : String) {
        try {

        val parsedEvtParam = Json.parse(EvtParametrage.serializer(), msg)
        println("Paramètre reçu : $parsedEvtParam")
        }
        catch (e: JsonDecodingException){
            println("Le message reçu n'est pas un JSON au bon format : $msg")
        }
        finally {
            // rien à faire
        }
    }



}