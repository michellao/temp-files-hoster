package com.example.shorturl

class UrlHandler(private val url: String) {
    companion object {
        private const val characters = "abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ-_!*0123456789"

        fun generatorURL(nbChar: Int): String {
            var strings = ""
            for (i in 0..nbChar) {
                strings += characters.random()
            }
            return strings
        }
    }

    fun getDotExtensionFile() = "." + url.substringAfterLast('.')
}