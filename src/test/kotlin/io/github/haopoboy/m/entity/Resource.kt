package io.github.haopoboy.m.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Resource(
        @Column(nullable = false) var name: String,
        @Column(columnDefinition = "text") var content: String = "") : UuidEntity()