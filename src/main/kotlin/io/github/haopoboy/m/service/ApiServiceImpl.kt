package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApiServiceImpl : ApiService {

    inner class Named(val name: String) : ApiService.Named {
        override fun query(criteria: Map<String, Any>): Map<String, Page> = query(name, criteria)
        override fun save(obj: Any) = save(name, obj)
        override fun save(list: List<*>) = save(name, list)
    }

    @Autowired
    private lateinit var modelService: ModelService

    @Autowired
    private lateinit var resourceService: ResourceService

    override fun forName(name: String): ApiService.Named {
        return Named(name)
    }

    override fun query(name: String, criteria: Map<String, Any>): Map<String, Page> {
        val definition = resourceService.getDefinition(name)
        return modelService.query(definition.queries)
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