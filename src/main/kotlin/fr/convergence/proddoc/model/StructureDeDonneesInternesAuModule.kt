package fr.convergence.proddoc.model

import fr.convergence.proddoc.model.lib.obj.MaskMessage
import fr.convergence.proddoc.model.lib.serdes.LocalDateTimeSerializer
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MaskLot (
    @Required
    val idUniqueDeLaDemandeALOrigineDuLot: String,
    @Required
    val idLot : String,
    @Required
    val idEmetteur: String?,
    @Required
    val idGreffe: String,
    val typeDemande: String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateDeReceptionDuLot: LocalDateTime,
    val codeUtilisateur: String,
    val produits : MutableList<MaskProduit>?,
    val actions : MutableList<MaskAction>?,
    var peutOnDemarrerInterpretation : Boolean = false
)

@Serializable
data class MaskProduit(
    val typeEvenement : String, // PRODUIT ou evenement type GO_INTERPRETATION ( pour gerer la chaine d execution )
    val evenement: MaskEvenement
)

@Serializable
data class MaskEvenement (
    val idEvenement : String,
    val codeProduit : String, // RCS_KBIS,etc,...,ainsi que LANCEMENT_INTERPRETATION
    val mapObjetMetier : Map<String,String>?, //   {"REGISTRE": 75302}
    val listeTypesLignesGerees : List<String>?, //
    val listeIndicateur : List<String>?, //["VISUKBIS"]
    val nombreExemplaires : Int?,
    val pourApostille : Boolean?,
    val produit : String?,
    val sortieLot : String?,
    val reference : String?,
    val listeIndicateurEntite : List<String>?,
    val sortieEdition: String?,
    val mapSortieDestinataire : Map<String,String>?,
    val description : String?,
    val descriptionKbis : String?,
    val typeDocumentGed : String?,
    val dateTarif : String?,
    val mapDestinataires : Map<String,String>?,
    val listSortieDestinataire : List<String>?
)

@Serializable
data class MaskAction (
    val maskMessage: MaskMessage,
    val idEvenementDuquelDependCetteAction : String, // On doit le retrouver dans la liste des MaskActions de MaskLot
    val infosTopicSurLequelOnPosteLeMessage : String,
    var actionEtat : ActionEtat // Enum
)

data class ClefAccesAuxLots(
    val idEmetteur: String,
    val idGreffe: String,
    val idLot : String
)


enum class ActionEtat{
    ACTION_NON_REALISEE,
    ACTION_REALISEE_AVEC_SUCCES,
    ACTION_REALISEE_EN_ECHEC
}