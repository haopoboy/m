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
        val page = impl.query(queries)
        assertThat(page.content).extracting("name").contains("resource", "resourceMultiple")
    }

    @Test
    fun queries() {
        val queries = mutableMapOf("first" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()), "second" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()))

        val map = impl.query(queries).content[0] as Map<String, Page>
        assertThat(map).containsKeys("first", "second")

        val first = map["first"] as Page
        assertThat(first.content).extracting("name").contains("resource", "resourceMultiple")
        val second = map["second"] as Page
        assertThat(second.content).extracting("name").contains("resource", "resourceMultiple")
    }

    @Test
    fun queryByName() {
        val queries = mutableMapOf("first" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()), "second" to Definition.Query.of("""
            select new map(r.name as name) from Resource r
        """.trimIndent()))
        val first = impl.query(queries, listOf("first"))
        assertThat(first.content).extracting("name").contains("resource", "resourceMultiple")

        val second = impl.query(queries, listOf("first"))
        assertThat(second.content).extracting("name").contains("resource", "resourceMultiple")
    }
}
