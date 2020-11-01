package io.github.haopoboy.m

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(MApplication::class)
annotation class EnableProjectM