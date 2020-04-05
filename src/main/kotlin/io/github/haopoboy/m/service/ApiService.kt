package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Page
import org.springframework.data.domain.Pageable

interface ApiService {
    interface Named {
        fun get(criteria: Map<String, Any?> = mapOf(), pageable: Pageable = Pageable.unpaged()): org.springframework.data.domain.Page<Any>
        fun query(criteria: Map<String, Any?> = mapOf()): Map<String, Page>
        fun save(obj: Any)
        fun save(list: List<*>)
    }

    fun forName(name: String): Named
    fun get(name: String, criteria: Map<String, Any?> = mapOf(), pageable: Pageable = Pageable.unpaged()): org.springframework.data.domain.Page<Any>
    fun query(name: String, criteria: Map<String, Any?> = mapOf()): Map<String, Page>
    fun save(name: String, obj: Any)
    fun save(name: String, list: List<*>)
}