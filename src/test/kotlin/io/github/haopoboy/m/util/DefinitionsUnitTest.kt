package io.github.haopoboy.m.util

import io.github.haopoboy.m.entity.Person
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class DefinitionsUnitTest {

    @Test
    fun atLeastOne() {
        assertThatThrownBy {
            Definitions.validate(from("""
        """.trimIndent()))
        }.withFailMessage("At least one persistent or query exists")

        assertThatThrownBy {
            Definitions.validate(from("""
            persistent:
        """.trimIndent()))
        }.withFailMessage("At least one persistent or query exists")

        assertThatThrownBy {
            Definitions.validate(from("""
            queries:
        """.trimIndent()))
        }.withFailMessage("At least one persistent or query exists")
    }

    @Test
    fun persistent() {
        val d = from("""
            persistent:
              entity: io.github.haopoboy.m.entity.Person
        """.trimIndent())

        assertThat(d.persistent?.entity).isEqualTo(Person::class.java)
    }

    @Test
    fun persistentWithPivots() {
        val d = from("""
            persistent:
              entity: io.github.haopoboy.m.entity.Person
            pivots:
              first:
                jpql: >
                  select from
              second:
                jpql: >
                  select from
        """.trimIndent())
        assertThat(d.pivots).containsKeys("first", "second")
    }

    @Test
    fun queries() {
        val d = from("""
            queries:
              list:
                jpql: >
                  select from
        """.trimIndent())

        assertThat(d.queries).containsKeys("list")

        val list = d.queries?.get("list")
        assertThat(list?.jpql).isEqualTo("select from")
    }

    @Test
    fun queriesWithPivots() {
        val d = from("""
            queries:
              list:
                jpql: >
                  select from
                pivots:
                  first:
                    jpql: >
                      select from  
            pivots:
              third:
                jpql: >
                  select from
              fourth:
                jpql: >
                  select from
        """.trimIndent())

        assertThat(d.queries).containsKeys("list")
        assertThat(d.pivots).containsKeys("third", "fourth")

        val list = d.queries["list"] ?: error("List should not be null")
        assertThat(list.jpql.trim()).isEqualTo("select from")
        assertThat(list.pivots).containsKeys("first")
    }

    private fun from(value: String) = Definitions.fromYaml(value)
}