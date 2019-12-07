package io.github.haopoboy.m.repository

import io.github.haopoboy.m.entity.UuidEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UuidEntityRepository<T : UuidEntity> : JpaRepository<T, String>