package io.github.haopoboy.m.repository

import io.github.haopoboy.m.entity.Resource
import java.util.*

interface ResourceRepository : UuidEntityRepository<Resource> {
    fun findByName(name: String): Optional<Resource>
}