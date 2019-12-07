package io.github.haopoboy.m.model

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class Page(content: List<Any>, pageable: Pageable, total: Long) : PageImpl<Any>(content, pageable, total) {

    constructor(content: List<Any>) : this(content, Pageable.unpaged(), content.size.toLong())
}