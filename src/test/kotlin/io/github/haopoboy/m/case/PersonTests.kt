package io.github.haopoboy.m.case

import io.github.haopoboy.m.DataInitializer
import io.github.haopoboy.m.entity.Person
import io.github.haopoboy.m.service.ApiService
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
class PersonTests {

    @Autowired
    private lateinit var initializer: DataInitializer

    @Autowired
    private lateinit var api: ApiService

    @Before
    fun init() {
        initializer.import()
        val people = listOf(
                Person("Heisenberg"), Person("Jesse")
        )
        people.forEach { forName().save(it) }
    }

    @Test
    fun query() {
        forName().query().let {
            val page = it["list"] ?: error("Page list not found")
            assertThat(page.totalElements).isEqualTo(2)
            assertThat(page.content).extracting("name").contains("Heisenberg", "Jesse")
        }
    }

    fun forName(): ApiService.Named {
        return api.forName("person")
    }
}