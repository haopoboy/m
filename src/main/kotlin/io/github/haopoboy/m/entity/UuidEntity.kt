package io.github.haopoboy.m.entity

import java.io.Serializable
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class UuidEntity(
        @Id @GeneratedValue(generator = "uuid2")
        var uuid: UUID? = null,
        open var name: String = ""
) : Serializable {

    override fun toString(): String {
        return "${this::class.java.simpleName} {name=$name, uuid=$uuid}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UuidEntity

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid?.hashCode() ?: 0
    }
}