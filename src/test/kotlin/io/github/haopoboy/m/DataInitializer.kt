package io.github.haopoboy.m

import io.github.haopoboy.m.entity.Resource
import io.github.haopoboy.m.repository.ResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@Component
class DataInitializer {

    @Autowired
    private lateinit var loader: ResourceLoader

    @Autowired
    private lateinit var repo: ResourceRepository

    fun import() {
        val file = loader.getResource("classpath:model").file
        file.walk().filter { it.isFile }.forEach {
            val resource = Resource(it.name.replace(".yml", ""), it.readText())
            repo.save(resource)
        }
    }
}