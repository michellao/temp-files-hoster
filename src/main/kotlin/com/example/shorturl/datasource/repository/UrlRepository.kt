package com.example.shorturl.datasource.repository

import com.example.shorturl.datasource.Url
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UrlRepository : CrudRepository<Url, Int> {
    @Query("SELECT * FROM URL u WHERE u.url_path = :url AND u.expires_at >= CURRENT_TIMESTAMP")
    fun findByGeneratedUrl(@Param("url") generatedUrl: String): Url?
    @Query("SELECT * FROM URL u WHERE u.expires_at < CURRENT_TIMESTAMP")
    fun findExpiredUrls(): List<Url>
}