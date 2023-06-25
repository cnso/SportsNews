package org.jash.sportsnews.model

data class Page<T>(
    val countId: Any,
    val current: Int,
    val maxLimit: Any,
    val optimizeCountSql: Boolean,
    val orders: List<Any>,
    val pages: Int,
    val records: List<T>,
    val searchCount: Boolean,
    val size: Int,
    val total: Int
)