package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.Parametre
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ParametreCache {

    private var map = mutableMapOf<String, Parametre>()

    fun addParameter(parametre: Parametre) = map.put(computeKey(parametre), parametre)
    private fun computeKey(parametre: Parametre): String = "${parametre.code_domaine}_${parametre.code_sous_domaine}_${parametre.cle}_${parametre.chrono}"

    fun getParameter(domaine: String, sousDomaine: String, cle: String, chrono: String): Parametre = map.get(computeKey(domaine, sousDomaine, cle, chrono))!!
    private fun computeKey(domaine: String, sousDomaine: String, cle: String, chrono: String): String = "${domaine}_${sousDomaine}_${cle}_${chrono}"

    fun getAll() : MutableCollection<Parametre> = map.values
}