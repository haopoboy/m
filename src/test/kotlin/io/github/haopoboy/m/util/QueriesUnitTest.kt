package io.github.haopoboy.m.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QueriesUnitTest {

    @Test
    fun replaceAsCountQuery() {
        "SELECT new Map(p.name AS name) FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .contains("Person p")
        }

        // Native
        "SELECT p.name AS name FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .contains("Person p")
        }
    }
}