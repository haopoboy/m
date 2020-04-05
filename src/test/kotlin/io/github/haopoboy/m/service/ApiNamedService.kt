package io.github.haopoboy.m.service

import org.springframework.data.domain.Page

interface ApiNamedService {

    fun forResource(): Resource
    fun forPerson(): Person
    fun forTeam(): Team

    interface Resource : ApiService.Named {
        fun findByName(name: String): Page<io.github.haopoboy.m.entity.Resource>
    }

    interface Person : ApiService.Named {
        fun findByName(name: String): Page<io.github.haopoboy.m.entity.Person>
    }

    interface Team : ApiService.Named {
        fun findByName(name: String): Page<io.github.haopoboy.m.entity.Team>
    }
}