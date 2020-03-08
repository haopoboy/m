package io.github.haopoboy.m.entity

import javax.persistence.Entity

@Entity
class Person(var name: String) : UuidEntity()