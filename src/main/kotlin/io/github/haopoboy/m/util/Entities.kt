package io.github.haopoboy.m.util

import java.lang.reflect.Field
import javax.persistence.EmbeddedId
import javax.persistence.Id

class Entities {

    companion object {
        fun getIdName(clazz: Class<*>): String {
            return findFieldAnnotatedWithIds(clazz).name
        }

        fun findFieldAnnotatedWithIds(clazz: Class<*>): Field {
            val fields = mutableListOf<Field>()
            var current: Class<*>? = clazz
            while (null != current) {
                fields.addAll(current.declaredFields)
                current = current.superclass
            }
            return fields.firstOrNull {
                it.getDeclaredAnnotationsByType(Id::class.java).isNotEmpty()
                        || it.getDeclaredAnnotationsByType(EmbeddedId::class.java).isNotEmpty()
            } ?: error("@Id or @EmbeddedId not found")
        }
    }
}