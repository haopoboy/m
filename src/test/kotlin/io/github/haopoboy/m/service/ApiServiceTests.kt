package io.github.haopoboy.m.service

import io.github.haopoboy.m.DataInitializer
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
class ApiServiceTests {

    @Autowired
    private lateinit var service: ApiService
    @Autowired
    private lateinit var initializer: DataInitializer

    private var impl: ApiService.Named? = null
    private var implMultiple: ApiService.Named? = null

    @Before
    fun init() {
        initializer.import()
        impl = service.forName("resource")
        implMultiple = service.forName("resourceMultiple")
    }

    @Test
    fun queries() {
        val page = impl?.query()
        assertThat(page?.values?.first()).extracting("name").contains("resource", "resourceMultiple")
    }

    @Test
    fun queriesWithCriteria() {
        val page = impl?.query(mapOf("nice" to "job"))
    }

    @Test
    fun queriesMultiple() {
        val page = implMultiple?.query()
        println(page)
    }

}
