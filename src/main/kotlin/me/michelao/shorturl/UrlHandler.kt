package me.michelao.shorturl

class UrlHandler(private val url: String) {
    companion object {
        private const val CHARACTERS = "abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ-_!*0123456789"

        fun generatorURL(nbChar: Int): String {
            var strings = ""
            for (i in 0..nbChar - 1) {
                strings += CHARACTERS.random()
            }
            return strings
        }

        fun generatorToken(): String {
            var strings = ""
            for (i in 0..31) {
                strings += CHARACTERS.random()
            }
            return strings
        }
    }

    fun getDotExtensionFile() = "." + url.substringAfterLast('.')
}
