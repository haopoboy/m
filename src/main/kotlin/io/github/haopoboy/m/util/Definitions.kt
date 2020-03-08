package io.github.haopoboy.m.util

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.haopoboy.m.model.Definition
import org.yaml.snakeyaml.Yaml

class Definitions {

    companion object {

        private val objectMapper = ObjectMapper()

        fun isNotEmpty(map: Map<*, *>?): Boolean {
            return (map != null) && map.isNotEmpty()
        }

        fun initialize(definition: Definition) {
            definition.persistent?.let { persistent ->
                val properties = persistent.properties.mapValues {
                    it.value ?: Definition.Persistent.Property()
                }
                persistent.properties = properties
            }
        }

        fun validate(definition: Definition) {
            check(definition.persistent != null || isNotEmpty(definition.queries)) {
                "At least one persistent or query exists"
            }
        }

        fun <T> fromYaml(def: String, clazz: Class<T>): T {
            val map = Yaml().loadAs(def, Map::class.java)
            return objectMapper.convertValue(map, clazz)
        }

        fun fromYaml(value: String): Definition {
            val map = Yaml().loadAs(value, Map::class.java)
            return objectMapper.convertValue(map, Definition::class.java)
        }

        fun validate(query: Definition.Query) {
            check(query.jpql!!.isNotBlank() || query.native!!.isNotBlank()) {
                "At least one jqpl or native exists"
            }
        }
    }
}