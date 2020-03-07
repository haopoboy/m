package io.github.haopoboy.m.service

import io.github.haopoboy.m.DataInitializer
import io.github.haopoboy.m.model.Definition
import io.github.haopoboy.m.model.Page
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class ModelServiceTests {

    @Autowired
    private lateinit var impl: ModelService

    @Autowired
    private lateinit var initializer: DataInitializer

    @Before
    fun init() {
        initializer.import()
    }

    @Test
    fun query() {
        val queries = mutableMapOf("default" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()))
        val map = impl.query(queries)
        assertThat(map.values.first()).extracting("name").contains("resource", "resourceMultiple")
    }

    @Test
    fun queries() {
        val queries = mutableMapOf("first" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()), "second" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()))

        val map = impl.query(queries)
        assertThat(map).containsKeys("first", "second")

        val first = map["first"] as Page
        assertThat(first.content).extracting("name").contains("resource", "resourceMultiple")
        val second = map["second"] as Page
        assertThat(second.content).extracting("name").contains("resource", "resourceMultiple")
    }

    @Test
    fun queryWithPivots() {
        val query = Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()).apply {
            val state = Definition.Query.of("""
                select new map(count(r) AS count) from Resource r
            """.trimIndent())
            state.extractFirst = true
            this.pivots = mapOf("state" to state)
        }

        impl.query(query).apply {
            assertThat(this.content).extracting("name").contains("resource", "resourceMultiple")
            assertThat(this.content).extracting("state")
                    .doesNotContainNull()
                    .isNotEmpty()
        }
    }
}
