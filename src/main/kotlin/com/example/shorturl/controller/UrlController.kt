package com.example.shorturl.controller

import com.example.shorturl.datasource.repository.UrlRepository
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlController(private val urlRepository: UrlRepository) {}