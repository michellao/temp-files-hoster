package me.michelao.shorturl.datasource

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.mapping.MappingException
import java.util.Date

data class AboutApp(
    val branch: String,
    val shortCommit: String,
    val commit: String,
)

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
    var expiresAt: Date,
    @Id var id: Int? = null
) {
    init {
        if (sizeMebiBytes > 512L) {
             throw MappingException("File must be smaller than 512 MiB")
        }
    }
}
