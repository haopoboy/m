package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Definition
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.ConstructorException

class ResourceServiceUnitTest {

    /**
     * Parse to class is not supported now, check it if supported someday.
     */
    @Test(expected = ConstructorException::class)
    fun loadAsNotSupportedToClassTypeOfProperties() {
        val yaml = """
            entity: com.github.haopoboy.m.entity.Resource
            properties:
              content:
        """.trimIndent()

        Yaml().loadAs(yaml, Definition.Persistent::class.java)
    }
}