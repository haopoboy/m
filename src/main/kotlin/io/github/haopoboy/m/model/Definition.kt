package io.github.haopoboy.m.model

import org.springframework.data.domain.Pageable

class Definition {

    var persistent: Persistent? = null
    var queries: Map<String, Query> = emptyMap()
    var pivots: Map<String, Query> = emptyMap()

    class Persistent {
        var entity: Class<Any>? = null
        var properties: Map<String, Object> = emptyMap()
    }

    class Query {
        companion object {
            fun of(jpql: String): Query {
                val q = Query()
                q.jpql = jpql
                return q
            }
        }

        var jpql: String = ""
        var native: String = ""
        var extractFirst = false
        var pageable: Pageable = Pageable.unpaged()
        var pivots: Map<String, Query> = emptyMap()
    }
}