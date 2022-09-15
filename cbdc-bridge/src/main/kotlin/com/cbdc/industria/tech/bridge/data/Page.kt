package com.cbdc.industria.tech.bridge.data

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
data class Page<T: Any>(
    val data: List<T>,
    val first: Boolean,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val pageSize: Int,
    val pageIndex: Int,
    val pageNumberOfElements: Int
)
