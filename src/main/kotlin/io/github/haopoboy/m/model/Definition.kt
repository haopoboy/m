package io.github.haopoboy.m.model

import org.springframework.data.domain.Pageable

class Definition(var persistent: Persistent? = null,
                 var queries: Map<String, Query> = mapOf(),
                 var pivots: Map<String, Query> = mapOf()) {

    data class Persistent(
            var entity: Class<Any>? = null,
            var properties: Map<String, Property?> = mapOf()
    ) {
        data class Property(var readonly: Boolean = false)
    }

    data class Query(var jpql: String = "",
                     var native: String = "",
                     var appends: List<Append> = listOf(),
                     var extractFirst: Boolean = false,
                     var pageable: Pageable = Pageable.unpaged(),
                     var pivots: Map<String, Query> = mapOf()) {

        companion object {
            fun of(jpql: String): Query {
                val q = Query()
                q.jpql = jpql
                return q
            }
        }

        data class Append(var statement: String = "")
    }
}