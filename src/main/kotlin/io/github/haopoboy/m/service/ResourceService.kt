package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Definition


interface ResourceService {
    fun getDefinition(name: String): Definition
}