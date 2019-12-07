package io.github.haopoboy.m.service

import io.github.haopoboy.m.DataInitializer
import io.github.haopoboy.m.entity.Resource
import io.github.haopoboy.m.repository.ResourceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class ResourceServiceTests {

    @Autowired
    private lateinit var initializer: DataInitializer

    @Autowired
    private lateinit var repo: ResourceRepository

    @Autowired
    private lateinit var service: ResourceService

    @Before
    fun init() {
        initializer.import()
    }

    @After
    fun rollback() {
        repo.deleteAll()
    }

    @Test
    fun count() {
        assertThat(repo.count()).isNotZero()
    }

    @Test
    fun resource() {
        val resource = service.getDefinition("resource")
        assertThat(resource.persistent?.entity).isEqualTo(Resource::class.java)

        val queries = resource.queries
        assertThat(queries).isNotEmpty.containsKeys("list", "nativeList")
        assertThat(queries["list"]?.jpql).isNotBlank()
        assertThat(queries["nativeList"]?.native).isNotBlank()

        val pivots = resource.pivots
        assertThat(pivots).isNotEmpty
    }
}
