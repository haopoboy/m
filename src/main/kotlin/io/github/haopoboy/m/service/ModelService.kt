package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Definition
import io.github.haopoboy.m.model.Page

interface ModelService {
    fun query(queries: Map<String, Definition.Query>): Map<String, Page>
    fun query(query: Definition.Query, criteria: Map<String, Any> = mapOf()): Page
}