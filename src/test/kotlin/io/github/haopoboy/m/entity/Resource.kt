package io.github.haopoboy.m.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class Resource(override var name: String? = null,
                    @Column(columnDefinition = "text")
                    var content: String? = null) : UuidEntity()