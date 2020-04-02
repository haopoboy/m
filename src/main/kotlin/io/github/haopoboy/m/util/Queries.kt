package io.github.haopoboy.m.util

import javax.persistence.Query

class Queries {

    companion object {
        private val REPLACE_REGEX = "(?i)\\bFROM\\b".toRegex()

        fun applyCriteria(query: Query, criteria: Map<String, Any?>) {
            query.parameters.forEach {
                query.setParameter(it.name, criteria[it.name])
            }
        }

        fun replaceAsCountQuery(query: String): String {
            val statements = query.split(REPLACE_REGEX)
            return """
                select count(*) from
                ${statements.subList(1, statements.size).joinToString("from")}
            """.trimIndent()
        }
    }
}