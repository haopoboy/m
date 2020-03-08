package io.github.haopoboy.m.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.support.Repositories

@Configuration
class ApplicationConfig {

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    @ConditionalOnMissingBean
    fun repositories(applicationContext: AnnotationConfigApplicationContext): Repositories {
        return Repositories(applicationContext)
    }
}