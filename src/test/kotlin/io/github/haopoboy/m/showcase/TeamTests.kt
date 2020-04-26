package io.github.haopoboy.m.showcase

import io.github.haopoboy.m.DataInitializer
import io.github.haopoboy.m.service.ApiNamedService
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
class TeamTests {

    @Autowired
    private lateinit var initializer: DataInitializer

    @Autowired
    private lateinit var api: ApiNamedService

    @Before
    fun init() {
        initializer.importHr()
    }

    @Test
    fun query() {
        api.forTeam().query().values.first().apply {
            assertThat(this.content).extracting("name").contains("Breaking Bad")
            assertThat(this.content)
                    .flatExtracting("people")
                    .extracting("name").contains("Heisenberg", "Jesse")
        }
    }
}