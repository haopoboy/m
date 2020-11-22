package io.github.haopoboy.m.model

import io.github.haopoboy.m.entity.Person
import io.github.haopoboy.m.util.Definitions
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.ConstructorException


class DefinitionUnitTest {

    @Test
    fun init() {
        val d = Yaml().loadAs("""
            persistent:
        """.trimIndent(), Definition::class.java)
        assertThat(d.persistent).isNull()
        assertThat(d.queries).isEmpty()
        assertThat(d.pivots).isEmpty()
    }

    @Test
    fun initPersistent() {
        val p = Yaml().loadAs("""
            entity: 
        """.trimIndent(), Definition.Persistent::class.java)

        assertThat(p.entity).isNull()
    }

    @Test
    fun initQuery() {
        assertThat(Yaml().loadAs("""
            jpql: select from
        """.trimIndent(), Definition.Query::class.java).native).isEmpty()
        assertThat(Yaml().loadAs("""
            native: select from
        """.trimIndent(), Definition.Query::class.java).jpql).isEmpty()
    }

    @Test
    fun persistent() {
        val d = Definitions.fromYaml("""
            entity: io.github.haopoboy.m.entity.Person
            properties:
              name:
        """, Definition.Persistent::class.java)

        assertThat(d.entity).isEqualTo(Person::class.java)
        assertThat(d.properties).containsKey("name")
    }

    @Test
    fun `should be thrown on nonexistent entity`() {
        val yaml = """
            entity: none.entity.Resource
            properties:
              content:
        """.trimIndent()

        Assertions.assertThatThrownBy {
            Yaml().loadAs(yaml, Definition.Persistent::class.java)
        }.isInstanceOf(ConstructorException::class.java)
    }
}