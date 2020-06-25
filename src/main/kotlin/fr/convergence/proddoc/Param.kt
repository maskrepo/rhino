package fr.convergence.proddoc

data class Param ( val before:String = "",
                   val after: Param2 = Param2(),
                   val op: String = "",
                   val ts_ms: String = "",
                   val transaction: String = "")
