package me.michelao.shorturl.configuration

import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.inputStream
import kotlin.io.path.writeText

class EncryptionManagement {
    private val pathFile = "./secret.key"
    private val algorithm = "AES"
    private val bits = 256
    lateinit var key: SecretKey
    private val keyGenerator = KeyGenerator.getInstance(algorithm)

    fun autoSetup() {
        if (Path(pathFile).exists()) {
            readFromFile()
        } else {
            createKey()
            saveAsFile()
        }
    }

    fun createKey() {
        keyGenerator.init(bits)
        key = keyGenerator.generateKey()
    }

    fun saveAsFile() {
        val dataEncoded = Base64.encode(key.encoded)
        Path(pathFile).writeText(dataEncoded)
    }

    fun readFromFile() {
        val inputStream = Path(pathFile).inputStream()
        val keyDecoded = Base64.decode(inputStream.readBytes())
        key = SecretKeySpec(keyDecoded, algorithm)
    }

    fun getSecretKey() = key
}
