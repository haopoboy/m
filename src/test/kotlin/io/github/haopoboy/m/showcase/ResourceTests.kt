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
class ResourceTests {

    @Autowired
    private lateinit var initializer: DataInitializer

    @Autowired
    private lateinit var api: ApiNamedService

    private lateinit var impl: ApiNamedService.Resource

    @BeforeAll
    fun init() {
        initializer.import()
        impl = api.forResource()
    }

    @Test
    fun get() {
        impl.get(mapOf("name" to "resource")).apply {
            assertThat(this).extracting("name").containsOnlyOnce("resource")
        }
    }

    @Test
    fun findByName() {
        impl.findByName("nothing").apply {
            assertThat(this).isEmpty()
        }
        impl.findByName("resource").apply {
            assertThat(this)
                    .extracting("name")
                    .contains("resource", "resourceMultiple", "resourceNative")
        }
    }

    @Test
    fun query() {
        impl.query().let {
            val page = it["list"] ?: error("Page list not found")
            assertThat(page.content)
                    .extracting("name")
                    .contains("resource", "resourceMultiple", "resourceNative")
        }
    }
}