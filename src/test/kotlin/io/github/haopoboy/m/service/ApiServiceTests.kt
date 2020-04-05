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

    private lateinit var impl: ApiService.Named
    private lateinit var multipleImpl: ApiService.Named
    private lateinit var nativeImpl: ApiService.Named

    @Before
    fun init() {
        initializer.import()
        impl = service.forName("resource")
        multipleImpl = service.forName("resourceMultiple")
        nativeImpl = service.forName("resourceNative")
    }

    @Test
    fun get() {
        impl.get().apply {
            assertThat(this).extracting("name")
                    .contains("resource", "resourceMultiple", "resourceNative")
        }
    }

    @Test
    fun query() {
        impl.query().values.first().apply {
            assertThat(this).extracting("name")
                    .contains("resource", "resourceMultiple", "resourceNative")
        }
    }

    @Test
    fun queryMultiple() {
        multipleImpl.query().apply {
            assertThat(this).containsKeys("list", "nativeList")
        }
    }

    @Test
    fun queryNative() {
        nativeImpl.query().apply {
            assertThat(this).containsKey("list")
        }
    }

    @Test
    fun queryWithCriteria() {
        impl.query(mapOf("name" to "job")).apply {
            assertThat(this.values.first().content).isEmpty()
        }
        impl.query(mapOf("name" to "resource")).apply {
            println(this.values.first().content)
            assertThat(this.values.first().content).hasSize(1)
        }
    }
}
