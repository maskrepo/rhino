package fr.convergence.proddoc

import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import kotlinx.serialization.json.*


@ApplicationScoped
class Consommateur {

    //Créaton d'un cache local
    var localCache = CaffeineCache()

    @Incoming("mygreffe")
    fun getMessage(msg: String) {

        try {
            //parsing du message reçu
            val parsedEvtParam = Json.parse(EvtParametrage.serializer(), msg)
            println("Paramètre reçu : $parsedEvtParam")

            // ajout dans le cache
            if (!localCache.ajouter(parsedEvtParam.cle, parsedEvtParam.valeur)) println("impossible d'ajouter dans le cache : clé existante")

            //@TODO la suite c'est vérifier si paramètre déjà dans le cache et émettre un évènement sur le topic si c'est le cas

        } catch (e: JsonDecodingException) {
            println("Le message reçu n'est pas un JSON au bon format : $msg")
        } finally {
            // rien à faire
            println("taille du cache local : ${localCache.tailleCache()}")
        }

    }

}