package io.github.haopoboy.m.entity

import javax.persistence.Entity

@Entity
class Person(val name: String) : UuidEntity() {
}