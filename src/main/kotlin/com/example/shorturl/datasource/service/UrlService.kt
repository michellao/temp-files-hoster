package com.example.shorturl.datasource.service

import com.example.shorturl.datasource.Url
import com.example.shorturl.datasource.repository.UrlRepository
import org.springframework.stereotype.Service

@Service
class UrlService(private val db: UrlRepository) {
    fun save(url: Url): Url = db.save(url)
    fun findByUrl(urlPath: String): Url? = db.findByGeneratedUrl(urlPath)
}