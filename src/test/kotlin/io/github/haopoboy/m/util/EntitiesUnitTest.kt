package io.github.haopoboy.m.util

import io.github.haopoboy.m.entity.Person
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EntitiesUnitTest {

    @Test
    fun getIdName() {
        assertThat(Entities.getIdName(Person::class.java)).isEqualTo("uuid")
    }

    @Test
    fun findNameAnnotatedWithIds() {
        Entities.findFieldAnnotatedWithIds(Person::class.java).apply {
            assertThat(this.name).isEqualTo("uuid")
        }
    }
}