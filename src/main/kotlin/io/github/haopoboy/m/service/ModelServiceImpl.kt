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

    override fun query(queries: Map<String, Definition.Query>, criteria: Map<String, Any>): Map<String, Page> {
        return queries
                .map { it.key to query(it.value, criteria) }
                .toMap()
    }

    @Suppress("UNCHECKED_CAST")
    override fun query(definition: Definition.Query, criteria: Map<String, Any>): Page {
        // Query
        val query = if (!definition.jpql.isBlank()) {
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

        // Page with pivots
        val content = injectPivots(
                query.resultList as List<Map<String, Any>>,
                criteria,
                definition.pivots
        )
        return Page(content, pageable, count(definition))
    }

    @Suppress("UNCHECKED_CAST")
    fun injectPivots(content: List<Map<String, Any>>,
                     criteria: Map<String, Any>,
                     pivots: Map<String, Definition.Query>
    ): List<Map<String, Any>> {
        return content.map { row ->
            val newCriteria = criteria.toMutableMap().apply {
                this.putAll(row)
            }

            val mutableRow = row.toMutableMap()
            pivots.forEach { (key, value) ->
                val contentWithPivots = query(value, newCriteria).apply {
                    injectPivots(this.content as List<Map<String, Any>>, newCriteria, value.pivots)
                }
                mutableRow[key] = if (value.extractFirst) {
                    contentWithPivots.first()
                } else {
                    contentWithPivots.content
                }
            }
            mutableRow.toMap()
        }
    }

    fun count(query: Definition.Query): Long {
        // TODO Count the query
        return 0
    }
}