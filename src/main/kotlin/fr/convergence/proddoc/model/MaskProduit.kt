package fr.convergence.proddoc.model

data class MaskProduit(
    val idEvenement: String,
    val codeProduit: String, // RCS_KBIS,etc,...,ainsi que LANCEMENT_INTERPRETATION
    val mapObjetMetier: Map<String, String>?, //   {"REGISTRE": 75302}
    val listeTypesLignesGerees: List<String>?, //
    val listeIndicateur: List<String>?, //["VISUKBIS"]
    val nombreExemplaires: Int?,
    val pourApostille: Boolean?,
    val produit: String?,
    val sortieLot: String?,
    val reference: String?,
    val listeIndicateurEntite: List<String>?,
    val sortieEdition: String?,
    val mapSortieDestinataire: Map<String, String>?,
    val description: String?,
    val descriptionKbis: String?,
    val typeDocumentGed: String?,
    val dateTarif: String?,
    val mapDestinataires: Map<String, String>?,
    val listSortieDestinataire: List<String>?,
    val actionsDeGenerations: MutableList<MaskActionDeGeneration>
)