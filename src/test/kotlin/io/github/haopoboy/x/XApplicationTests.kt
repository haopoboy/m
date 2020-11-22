package io.github.haopoboy.x

import io.github.haopoboy.m.service.ApiService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class XApplicationTests {

    @Autowired
    private lateinit var api: ApiService

    @Test
    fun contextLoads() {
        assertThat(api).isNotNull
    }

}
