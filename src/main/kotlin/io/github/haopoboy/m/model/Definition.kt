package io.github.haopoboy.m.model

import org.springframework.data.domain.Pageable

class Definition(var persistent: Persistent? = null,
                 var queries: Map<String, Query> = emptyMap(),
                 var pivots: Map<String, Query> = emptyMap()) {

    data class Persistent(
            var entity: Class<Any>? = null,
            var properties: Map<String, Object> = emptyMap()
    )

    data class Query(var jpql: String = "",
                     var native: String = "",
                     var extractFirst: Boolean = false,
                     var pageable: Pageable = Pageable.unpaged(),
                     var pivots: Map<String, Query> = emptyMap()) {

        companion object {
            fun of(jpql: String): Query {
                val q = Query()
                q.jpql = jpql
                return q
            }
        }
    }
}