package io.github.haopoboy.m.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CommonsUnitTest {

    @Test
    fun objectMapper() {
        assertThat(Commons.OBJECT_MAPPER).isNotNull
    }
}