package fr.convergence.proddoc

import fr.convergence.proddoc.model.Parametre
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GestionParametre {

    @Incoming("parametre")
    fun ecoute(chaine: Parametre) {
        println("${chaine.cle} -> ${chaine.valeur}");
    }
}