package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Page

interface ApiService {
    interface Named {
        fun query(criteria: Map<String, Any> = mapOf()): Map<String, Page>
        fun save(obj: Any)
        fun save(list: List<*>)
    }

    fun forName(name: String): Named
    fun query(name: String, criteria: Map<String, Any> = mapOf()): Map<String, Page>
    fun save(name: String, obj: Any)
    fun save(name: String, list: List<*>)
}