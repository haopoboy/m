package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Page

interface ApiService {
    interface Named {
        fun query(criteria: Map<String, Any> = mapOf()): Map<String, Page>
    }

    fun forName(name: String): Named
    fun query(name: String, criteria: Map<String, Any> = mapOf()): Map<String, Page>
}