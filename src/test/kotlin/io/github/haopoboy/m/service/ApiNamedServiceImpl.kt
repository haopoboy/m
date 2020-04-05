package io.github.haopoboy.m.service

import io.github.haopoboy.m.entity.Person
import io.github.haopoboy.m.entity.Resource
import io.github.haopoboy.m.entity.Team
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service


@Service
class ApiNamedServiceImpl : ApiNamedService {
    @Autowired
    private lateinit var api: ApiService

    override fun forResource(): ApiNamedService.Resource {
        return ResourceImpl()
    }

    override fun forPerson(): ApiNamedService.Person {
        return PersonImpl()
    }

    override fun forTeam(): ApiNamedService.Team {
        return TeamImpl()
    }

    inner class ResourceImpl : ApiNamedService.Resource, ApiServiceImpl.Named(api, "resource") {
        override fun findByName(name: String): Page<Resource> {
            return get(mapOf("name" to name)) as Page<Resource>
        }
    }

    inner class PersonImpl : ApiNamedService.Person, ApiServiceImpl.Named(api, "person") {
        @Suppress("UNCHECKED_CAST")
        override fun findByName(name: String): Page<Person> {
            return get(mapOf("name" to name)) as Page<Person>
        }

    }

    inner class TeamImpl : ApiNamedService.Team, ApiServiceImpl.Named(api, "team") {
        override fun findByName(name: String): Page<Team> {
            return get(mapOf("name" to name)) as Page<Team>
        }
    }
}