package com.example.shorturl.datasource

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id

@Table("ACCOUNT")
data class Account(
    val apiKey: String,
    val isAdmin: Boolean = false,
    @Id var id: Int?
)

@Table("URL")
data class Url(
    val originalFilename: String,
    val urlPath: String,
    val mimeType: String,
    val account: Account?,
    @Id var id: Int?
)