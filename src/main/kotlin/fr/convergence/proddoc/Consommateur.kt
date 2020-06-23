package fr.convergence.proddoc

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecodingException
import org.apache.avro.file.DataFileReader
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.io.DatumReader
import org.apache.avro.io.JsonDecoder
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.util.Utf8
import org.eclipse.microprofile.reactive.messaging.Incoming
import rhino.message.Parametre
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class Consommateur {

    //Créaton d'un cache local
    var localCache = CaffeineCache()

    @Incoming("mygreffe")
    fun getMessage(msg: String) {

        try {
            // parsing du message reçu avec kotlinx.serialization
            val parsedEvtParam = Json.parse(EvtParametrage.serializer(), msg)
            println("Paramètre reçu parsed avec kotlinx.serialization : $parsedEvtParam")

            // parsing du message reçu avec apache avro
            /* var avroMsg = Parametre("Alain","Deloin")
            val MsgDatumWriter: DatumWriter<Parametre> = SpecificDatumWriter<Parametre>(Parametre::class.java)
            println("Message serialisé avec AVRO : ${MsgDatumWriter.toString()}") */

       /*     var msgReader : DatumReader<Parametre> = SpecificDatumReader<Parametre>(Parametre::class.java)
            val msgByteArray = msg.toByteArray(charset("Utf8"))
            msgReader.read(msg)
*/
            val evtMsgDeserialized = "toto"
            println("Parametre reçu et deserialise avec AVRO : $evtMsgDeserialized")

            // ajout dans le cache
            if (!localCache.ajouter(parsedEvtParam.cle, parsedEvtParam.valeur)) println("impossible d'ajouter dans le cache : clé existante")

            //@TODO la suite c'est vérifier si paramètre déjà dans le cache et émettre un évènement sur le topic si c'est le cas

        } catch (e: JsonDecodingException) {
            println("Le message reçu n'est pas un JSON au bon format : $msg")
        }
        catch (e: Exception) {
            println("Un exception est survenue : ${e.message}")
        }

        finally {
            // rien à faire
            println("taille du cache local : ${localCache.tailleCache()}")
        }

    }

}