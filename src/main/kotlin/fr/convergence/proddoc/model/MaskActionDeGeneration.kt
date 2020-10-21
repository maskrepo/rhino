package fr.convergence.proddoc.model

import fr.convergence.proddoc.model.lib.obj.MaskMessage

data class MaskActionDeGeneration(
    var maskMessage: MaskMessage?,
    var actionDeGeneration : String,
    val actionsDeRestitution: MutableList<MaskActionDeRestitution>,
    var actionEtat: ActionEtat
)