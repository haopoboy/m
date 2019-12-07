package io.github.haopoboy.m.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.haopoboy.m.model.Definition
import io.github.haopoboy.m.repository.ResourceRepository
import io.github.haopoboy.m.util.Definitions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.yaml.snakeyaml.Yaml

@Service
class ResourceServiceImpl : ResourceService {

    @Autowired
    private lateinit var repo: ResourceRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun getDefinition(name: String): Definition {
        val resource = repo.findByName(name)
        if (resource.isPresent) {
            return toDefinition(resource.get().content)
        } else {
            throw IllegalArgumentException("Resource $name not found")
        }
    }

    fun toDefinition(content: String): Definition {
        val model = Yaml().load(content) as Map<String, Object>
        val definition = objectMapper.convertValue(model, Definition::class.java)
        Definitions.validate(definition)
        return definition
    }
}