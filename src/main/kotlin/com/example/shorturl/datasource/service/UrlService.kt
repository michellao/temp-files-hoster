package com.example.shorturl.datasource.service

import com.example.shorturl.datasource.Url
import com.example.shorturl.datasource.repository.UrlRepository

class UrlService(private val db: UrlRepository) {
    fun save(url: Url): Url = db.save(url)
}