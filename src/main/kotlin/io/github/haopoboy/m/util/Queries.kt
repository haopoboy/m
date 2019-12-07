package io.github.haopoboy.m.util

import javax.persistence.Query

class Queries {

    companion object {
        fun applyCriteria(query: Query, criteria: Map<String, Any>) {
            query.parameters.forEach {
                query.setParameter(it.name, criteria[it.name])
            }
        }
    }
}