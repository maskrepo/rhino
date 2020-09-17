package fr.convergence.proddoc.service

import fr.convergence.proddoc.model.metier.Lot
import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path

@ApplicationScoped
class LotService {

    val mapLot : ConcurrentHashMap<String, Lot> = ConcurrentHashMap()

}