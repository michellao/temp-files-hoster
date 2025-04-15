package com.example.shorturl.datasource

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection

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
    @MappedCollection(idColumn = "accountId", keyColumn = "id")
    val account: Account? = null,
    @Id var id: Int? = null
)