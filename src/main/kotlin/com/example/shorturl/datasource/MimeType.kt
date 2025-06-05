package com.example.shorturl.datasource

enum class MimeType(val data: String) {
    APPLICATION_PDF("application/pdf"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    TEXT_PLAIN("text/plain"),
    APPLICATION_JSON("application/json");

    override fun toString(): String = data

    companion object {
        fun fromValue(value: String): MimeType = entries.first { it.data == value }
    }
}