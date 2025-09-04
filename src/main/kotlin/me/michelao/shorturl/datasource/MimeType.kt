package me.michelao.shorturl.datasource

enum class MimeType(val data: String) {
    APPLICATION_PDF("application/pdf"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    TEXT_PLAIN("text/plain"),
    APPLICATION_ZIP("application/zip"),
    APPLICATION_X_7Z_COMPRESSED("application/x-7z-compressed"),
    APPLICATION_JSON("application/json");

    override fun toString(): String = data

    companion object {
        fun fromValue(value: String): MimeType = entries.first { it.data == value }
    }
}
