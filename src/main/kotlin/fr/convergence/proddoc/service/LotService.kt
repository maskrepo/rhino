package fr.convergence.proddoc.service

import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LotService {

    val mapLot: ConcurrentHashMap<String, String> = ConcurrentHashMap()

}