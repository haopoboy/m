package io.github.haopoboy.m.service

import io.github.haopoboy.m.model.Definition
import io.github.haopoboy.m.model.Page
import io.github.haopoboy.m.util.Entities
import io.github.haopoboy.m.util.Queries
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.support.Repositories
import org.springframework.stereotype.Service
import java.io.Serializable
import javax.persistence.EntityManager
import javax.persistence.Query

@Service
class ModelServiceImpl : ModelService {

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var repositories: Repositories

    override fun query(queries: Map<String, Definition.Query>, criteria: Map<String, Any?>): Map<String, Page> {
        return queries
                .map { it.key to query(it.value, criteria) }
                .toMap()
    }

    @Suppress("UNCHECKED_CAST")
    override fun query(query: Definition.Query, criteria: Map<String, Any?>): Page {
        val emQuery = createQuery(query)

        // Pageable
        val pageable = query.pageable
        if (pageable.isPaged) {
            emQuery.firstResult = pageable.pageNumber
            emQuery.maxResults = pageable.pageSize
        }

        // Criteria
        Queries.applyCriteria(emQuery, criteria)
        val resultList = queryForList(query, emQuery)
        // Page with pivots
        val content = injectPivots(
                resultList,
                criteria,
                query.pivots
        )
        return Page(content, pageable, count(query, criteria))
    }

    @Suppress("UNCHECKED_CAST")
    fun queryForList(query: Definition.Query, emQuery: Query): List<Map<String, Any?>> {
        val resultList = emQuery.resultList
        return if (query.native.isNotBlank()) {
            resultList.map {
                (listOf("name", "content") zip it as Array<Any?>).toMap()
            }
        } else {
            resultList as List<Map<String, Any?>>
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun injectPivots(content: List<Map<String, Any?>>,
                     criteria: Map<String, Any?>,
                     pivots: Map<String, Definition.Query>
    ): List<Map<String, Any?>> {
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

    fun createQuery(query: Definition.Query): Query {
        val statements = query.appends.joinToString(",") { it.statement }
        return if (query.jpql.isNotBlank()) {
            entityManager.createQuery("""
                ${query.jpql}
                $statements
            """.trimIndent())
        } else {
            entityManager.createNativeQuery("""
                ${query.native}
                $statements
            """.trimIndent())
        }
    }

    fun count(query: Definition.Query, criteria: Map<String, Any?>): Long {
        val statements = query.appends.joinToString(",") { it.statement }
        val countQuery = if (query.jpql.isNotBlank()) {
            entityManager.createQuery("""
                ${Queries.replaceAsCountQuery(query.jpql)}
                $statements
            """.trimIndent())
        } else {
            entityManager.createNativeQuery("""
                ${Queries.replaceAsCountQuery(query.native)}
                $statements
            """.trimIndent())
        }

        Queries.applyCriteria(countQuery, criteria)
        return (countQuery.singleResult as Number).toLong()
    }

    override fun save(persistent: Definition.Persistent, obj: Any) {
        val source = BeanWrapperImpl(obj)
        val entityClass = persistent.entity!!
        val id = getId(entityClass, source)

        @Suppress("UNCHECKED_CAST")
        val repo: CrudRepository<Serializable, Serializable> = repositories.getRepositoryFor(entityClass)
                .orElseThrow { error("Repository for $entityClass not found") }
                as CrudRepository<Serializable, Serializable>

        val target = if (null != id && repo.existsById(id)) {
            BeanWrapperImpl()
        } else {
            BeanWrapperImpl(entityClass)
        }

        convertValues(persistent.properties, source, target)
        repo.save(target.wrappedInstance as Serializable)
    }

    fun getId(entity: Class<*>, wrapper: BeanWrapper): Serializable? {
        val name = Entities.getIdName(entity)
        return wrapper.getPropertyValue(name) as Serializable?
    }

    fun convertValues(properties: Map<String, Definition.Persistent.Property?>, source: BeanWrapper, target: BeanWrapper) {
        properties.filter {
            !(it.value?.readonly
                    ?: error("Property '${it.key}' should not be null, call Definitions.initialize() first"))
        }.keys.forEach {
            val value = source.getPropertyValue(it)
            target.setPropertyValue(it, value)
        }
    }
}