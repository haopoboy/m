package io.github.haopoboy.m.entity

import javax.persistence.Entity

@Entity
data class Person(override var name: String? = null) : UuidEntity()
