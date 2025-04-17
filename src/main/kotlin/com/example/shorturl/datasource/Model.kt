package com.example.shorturl.datasource

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.mapping.MappingException
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.util.unit.DataSize
import java.util.Date
import kotlin.math.pow

@Table
data class Account(
    val apiKey: String,
    val isAdmin: Boolean = false,
    @Id var id: Int? = null
)

@Table
data class Url(
    val originalFilename: String?,
    val urlPath: String,
    val contentType: String,
    val sizeMebiBytes: Long,
    @MappedCollection(idColumn = "accountId", keyColumn = "id")
    val account: Account? = null,
    val expiresAt: Date = calculateExpiresAt(sizeMebiBytes),
    @Id var id: Int? = null
) {
    init {
        if (sizeMebiBytes > 512) {
             throw MappingException("File must be inferior than 512 Mega bytes")
        }
    }
    companion object {
        fun calculateExpiresAt(sizeMebiBytes: Long): Date {
            val now = System.currentTimeMillis()
            val minAge = 30
            val maxAge = 365
            val maxSize = 512
            val days = minAge + (minAge + maxAge) * (sizeMebiBytes / maxSize - 1).toDouble().pow(3)
            val daysInMillisecond = (days * 24 * 60 * 60 * 1000).toLong()
            return Date(now + daysInMillisecond)
        }
    }
}