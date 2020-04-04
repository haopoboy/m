package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Definition
import io.github.haopoboy.m.model.Page
import org.springframework.data.domain.Pageable

interface ModelService {
    fun get(persistent: Definition.Persistent,
            criteria: Map<String, Any?> = mapOf(),
            pageable: Pageable = Pageable.unpaged()
    ): org.springframework.data.domain.Page<Any>

    fun query(queries: Map<String, Definition.Query>, criteria: Map<String, Any?> = mapOf()): Map<String, Page>
    fun query(query: Definition.Query, criteria: Map<String, Any?> = mapOf()): Page
    fun save(persistent: Definition.Persistent, obj: Any)
}