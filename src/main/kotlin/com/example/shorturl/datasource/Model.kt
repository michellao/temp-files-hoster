package com.example.shorturl.datasource

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.mapping.MappingException
import java.util.Date

@Table
data class Account(
    val apiKey: String,
    val isAdmin: Boolean = false,
    val urls: Map<Int, Url>,
    @Id var id: Int? = null
)

@Table
data class Url(
    val originalFilename: String?,
    val urlPath: String,
    val contentType: MimeType,
    val sizeMebiBytes: Long,
    val token: String,
    val ipAddress: String,
    val userAgent: String?,
    val expiresAt: Date,
    @Id var id: Int? = null
) {
    init {
        if (sizeMebiBytes > 512) {
             throw MappingException("File must be inferior than 512 Mega bytes")
        }
    }
}