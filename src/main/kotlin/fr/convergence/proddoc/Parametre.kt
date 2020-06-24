package fr.convergence.proddoc

class PayLoadParametre: Payload<Parametre>() {

}

class Parametre {
     val cle: String? = null
     val valeur: String? = null
    private val identifiant: String? = null
    private val code_domaine: String? = null
    private val code_sous_domaine: String? = null
    private val chrono: String? = null
    private val type: String? = null
    private val type_java: String? = null
    private val commentaire: String? = null
    private val constante_java: String? = null
    private val indic_national: String? = null
    private val condition: String? = null
    private val nom_table_reference: String? = null
}