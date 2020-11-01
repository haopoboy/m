package io.github.haopoboy.m.config

import io.github.haopoboy.m.repository.UuidEntityRepository
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration::class)
@EnableJpaRepositories(basePackageClasses = [UuidEntityRepository::class])
class RepositoryConfig {
}