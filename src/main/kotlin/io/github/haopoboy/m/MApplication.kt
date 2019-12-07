package io.github.haopoboy.m

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MApplication

fun main(args: Array<String>) {
	runApplication<MApplication>(*args)
}
