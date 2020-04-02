package io.github.haopoboy.m.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QueriesUnitTest {

    @Test
    fun replaceAsCountQuery() {
        "SELECT new Map(p.name AS name) FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }
        "SELECT new Map(p.name AS name, p.name AS another) FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }
        "SELECT p.name FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }
        "SELECT p.name AS name FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }

        // Native
        "SELECT p.name FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }

        "SELECT p.name AS name FROM Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .contains("Person p")
        }

    }

    @Test
    fun replaceAsCountQueryWithSubQuery() {
        """
            SELECT new Map(p.name AS name) FROM Person p
            WHERE exists(SELECT pp FROM Person pp WHERE pp.uuid = p.uuid)
        """.trimIndent().apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) FROM")
                    .doesNotContain("p.name")
                    .contains("Person p")
                    .contains("exists(SELECT pp FROM Person pp WHERE pp.uuid = p.uuid)")
        }
    }
}