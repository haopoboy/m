package io.github.haopoboy.m.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QueriesUnitTest {

    @Test
    fun replaceAsCountQuery() {
        "select new Map(p.name AS name) from Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) from")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }
        "select new Map(p.name AS name, p.name AS another) from Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) from")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }
        "select p.name from Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) from")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }
        "select p.name AS name from Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) from")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }

        // Native
        "select p.name from Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) from")
                    .doesNotContain("p.name")
                    .contains("Person p")
        }

        "select p.name AS name from Person p".apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("SELECT count(*) from")
                    .contains("Person p")
        }

    }

    @Test
    fun replaceAsCountQueryWithSubQuery() {
        """
            select new Map(p.name AS name) from Person p
            WHERE exists(select pp from Person pp WHERE pp.uuid = p.uuid)
        """.trimIndent().apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .contains("select count(*) from")
                    .doesNotContain("p.name")
                    .contains("Person p")
                    .contains("exists(select pp from Person pp WHERE pp.uuid = p.uuid)")
        }
    }

    @Test
    fun replaceAsCountQueryWithManyFromOfNames() {
        """
            select new Map(p.name AS fromName) from Person p
            WHERE exists(select pp from Person pp WHERE pp.uuid = p.uuid)
        """.trimIndent().apply {
            assertThat(Queries.replaceAsCountQuery(this))
                    .doesNotContain("select new Map(p.name AS fromName)")
                    .contains("select count(*) from")
                    .contains("Person p")
                    .contains("WHERE exists(select pp from Person pp WHERE pp.uuid = p.uuid)")
        }
    }
}