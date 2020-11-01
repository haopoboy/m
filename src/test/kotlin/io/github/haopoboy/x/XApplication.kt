package io.github.haopoboy.x

import io.github.haopoboy.m.EnableProjectM
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableProjectM
class MApplication

fun main(args: Array<String>) {
    runApplication<MApplication>(*args)
}
