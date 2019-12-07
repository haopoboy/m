package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Definition
import io.github.haopoboy.m.model.Page
import io.github.haopoboy.m.util.Queries
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class ModelServiceImpl : ModelService {

    @Autowired
    private lateinit var entityManager: EntityManager

    override fun query(queries: Map<String, Definition.Query>, names: List<String>): Page {
        val results = queries
                .filter { names.isEmpty() || names.contains(it.key) }
                .map {
                    it.key to query(it.value)
                }.toMap()

        // Auto extract single result to the content
        return if (results.size == 1) {
            return results.values.toList()[0]
        } else {
            Page(listOf(results))
        }
    }

    override fun query(definition: Definition.Query, criteria: Map<String, Any>): Page {
        // Query
        val query = if (!definition.jpql.isNullOrBlank()) {
            entityManager.createQuery(definition.jpql)
        } else {
            entityManager.createNativeQuery(definition.native)
        }

        // Pageable
        val pageable = definition.pageable
        if (pageable.isPaged) {
            query.firstResult = pageable.pageNumber
            query.maxResults = pageable.pageSize
        }

        // Criteria
        Queries.applyCriteria(query, criteria)

        // Page
        val content = query.resultList as List<Any>
        return Page(content, pageable, count(definition))

    }

    fun count(query: Definition.Query): Long {
        return 0
    }
}