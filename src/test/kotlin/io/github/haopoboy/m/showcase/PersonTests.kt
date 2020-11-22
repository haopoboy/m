package io.github.haopoboy.m.showcase

import io.github.haopoboy.m.DataInitializer
import io.github.haopoboy.m.service.ApiNamedService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class PersonTests {

    @Autowired
    private lateinit var initializer: DataInitializer

    @Autowired
    private lateinit var api: ApiNamedService

    private lateinit var impl: ApiNamedService.Person

    @BeforeAll
    fun init() {
        initializer.importHr()
        impl = api.forPerson()
    }

    @Test
    fun get() {
        impl.get(mapOf("name" to "Heisenberg")).apply {
            assertThat(this).extracting("name").containsOnly("Heisenberg")
        }
    }

    @Test
    fun findByName() {
        impl.findByName("Heisenberg").apply {
            assertThat(this).extracting("name").containsOnly("Heisenberg")
        }
    }

    @Test
    fun query() {
        impl.query().let {
            val page = it["list"] ?: error("Page list not found")
            assertThat(page.totalElements).isEqualTo(2)
            assertThat(page.content).extracting("name").contains("Heisenberg", "Jesse")
        }
    }
}