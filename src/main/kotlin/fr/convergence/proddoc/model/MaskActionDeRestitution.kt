package fr.convergence.proddoc.model

import fr.convergence.proddoc.model.lib.obj.MaskMessage


data class MaskActionDeRestitution(
    val maskMessage: MaskMessage,
    var actionEtat: ActionEtat
)