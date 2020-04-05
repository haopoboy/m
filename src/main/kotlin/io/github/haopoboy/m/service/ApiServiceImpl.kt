package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ApiServiceImpl : ApiService {

    open class Named(val api: ApiService, val name: String) : ApiService.Named {
        override fun get(criteria: Map<String, Any?>, pageable: Pageable) = api.get(name, criteria, pageable)
        override fun query(criteria: Map<String, Any?>): Map<String, Page> = api.query(name, criteria)
        override fun save(obj: Any) = api.save(name, obj)
        override fun save(list: List<*>) = api.save(name, list)
    }

    @Autowired
    private lateinit var modelService: ModelService

    @Autowired
    private lateinit var resourceService: ResourceService

    override fun forName(name: String): ApiService.Named {
        return Named(this, name)
    }

    override fun get(name: String, criteria: Map<String, Any?>, pageable: Pageable): org.springframework.data.domain.Page<Any> {
        val definition = resourceService.getDefinition(name)
        return modelService.get(definition.persistent!!, criteria, pageable)
    }

    override fun query(name: String, criteria: Map<String, Any?>): Map<String, Page> {
        val definition = resourceService.getDefinition(name)
        return modelService.query(definition.queries, criteria)
    }

    override fun save(name: String, obj: Any) {
        save(name, listOf(obj))
    }

    override fun save(name: String, list: List<*>) {
        val definition = resourceService.getDefinition(name)
        list.filterNotNull().forEach {
            modelService.save(definition.persistent!!, it)
        }
    }
}