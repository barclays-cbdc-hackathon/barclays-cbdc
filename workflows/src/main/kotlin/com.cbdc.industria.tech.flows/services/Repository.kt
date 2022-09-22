package com.cbdc.industria.tech.flows.services

import net.corda.v5.application.injection.CordaInject
import net.corda.v5.application.services.CordaService
import net.corda.v5.application.services.persistence.PersistenceService
import net.corda.v5.ledger.contracts.StateAndRef
import net.corda.v5.ledger.schemas.PersistentState
import net.corda.v5.ledger.schemas.QueryableState
import kotlin.reflect.KProperty1

class Repository() : CordaService {

//    @CordaInject
//    lateinit var persistenceService: PersistenceService
//
//    inline fun <reified S : QueryableState, T : PersistentState, I : Any> customQuerySingle(
//        column: KProperty1<T, I?>,
//        id: I
//    ): StateAndRef<S>? {
//        val expression = builder { column.equal(id) }
//        val criteria = QueryCriteria.VaultCustomQueryCriteria(expression = expression)
//        return persistenceService.query<S>(criteria).states.singleOrNull()
//    }
//
//    inline fun <reified S : QueryableState, T : PersistentState, I : Comparable<I>> customQueryList(
//        column: KProperty1<T, I?>,
//        emails: Collection<I>
//    ): List<StateAndRef<S>> {
//        val expression = builder { column.`in`(emails) }
//        val criteria = QueryCriteria.VaultCustomQueryCriteria(expression = expression)
//        return serviceHub.vaultService.queryBy<S>(criteria).states
//    }
}
