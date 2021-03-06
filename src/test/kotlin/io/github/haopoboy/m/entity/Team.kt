package io.github.haopoboy.m.entity

import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
data class Team(override var name: String? = null,
                @ManyToMany
                var people: List<Person> = listOf()
) : UuidEntity()