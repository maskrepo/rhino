package fr.convergence.proddoc.model

import java.lang.System.*
import kotlin.system.measureTimeMillis

@MaskTable("p_parametre")
class Parametre {
    var cle: String? = null
    var valeur: String? = null
    var identifiant: String? = null
    var code_domaine: String? = null
    var code_sous_domaine: String? = null
    var chrono: String? = null
    var type: String? = null
    var type_java: String? = null
    var commentaire: String? = null
    var constante_java: String? = null
    var indic_national: String? = null
    var condition: String? = null
    var nom_table_reference: String? = null
    var timestamp = currentTimeMillis()

    override fun toString(): String {
        return "Parametre(${code_domaine}\\${code_sous_domaine}\\${cle}\\${chrono} => \"$valeur\")"
    }
}