package io.github.haopoboy.m.util

import javax.persistence.Query

class Queries {

    companion object {
        private val REPLACE_REGEX = "(?i)(SELECT)[^&]*(\\).?FROM)".toRegex()

        fun applyCriteria(query: Query, criteria: Map<String, Any>) {
            query.parameters.forEach {
                query.setParameter(it.name, criteria[it.name])
            }
        }

        fun replaceAsCountQuery(query: String): String {
            return """
                SELECT count(*) FROM
                ${query.replace(REPLACE_REGEX, "")}
            """.trimIndent()
        }
    }
}