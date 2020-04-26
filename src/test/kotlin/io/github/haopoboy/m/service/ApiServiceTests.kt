package io.github.haopoboy.m.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * See [package case][io.github.haopoboy.m.showcase] for testing and studying.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class ApiServiceTests {

    @Autowired
    private lateinit var impl: ApiService

    @Test
    fun forName() {
        impl.forName("resource").apply {
            assertThat(this).isNotNull
        }
    }
}
