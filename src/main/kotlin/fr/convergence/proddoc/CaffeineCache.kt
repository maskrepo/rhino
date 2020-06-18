package fr.convergence.proddoc

import com.github.benmanes.caffeine.cache.Caffeine

class CaffeineCache {

    private var cache = Caffeine.newBuilder()
        .initialCapacity(50) // taille initiale du cache
        .maximumSize(100) // taille maximale du cache
        .build<Any, Any>()

    fun ajouter(cle: String, valeur: String) : Boolean {
        if (cache.getIfPresent(cle) != null) return false
        else {
            cache.put(cle, valeur)
            return true
        }
    }

    fun ecraser(cle: String, valeur: String)  {
            cache.put(cle, valeur)
    }

    fun verifierPresence(cle: String) :String? {
        val valeur :String? = cache.getIfPresent(cle).toString()
        return(valeur)
    }

    fun tailleCache() = cache.estimatedSize()

}