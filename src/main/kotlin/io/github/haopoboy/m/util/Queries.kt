package io.github.haopoboy.m.util

import javax.persistence.Query

class Queries {

    companion object {
        private val REPLACE_REGEX = "(?i)FROM".toRegex()

        fun applyCriteria(query: Query, criteria: Map<String, Any?>) {
            query.parameters.forEach {
                query.setParameter(it.name, criteria[it.name])
            }
        }

        fun replaceAsCountQuery(query: String): String {
            val statements = query.split(REPLACE_REGEX)
            return """
                SELECT count(*) FROM
                ${statements.subList(1, statements.size).joinToString("FROM")}
            """.trimIndent()
        }
    }
}