package com.example.shorturl.controller

import com.example.shorturl.datasource.S3ClientData
import com.example.shorturl.datasource.service.UrlService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.logging.Logger

@Controller
class UrlController(private val service: UrlService, private val s3: S3ClientData) {
    private val logger = Logger.getLogger(this.javaClass.name)

    @GetMapping("/{generatedUrl}", produces = [MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getFile(@PathVariable("generatedUrl") generatedUrl: String): ResponseEntity<ByteArray> {
        logger.info("Access to: $generatedUrl")
        service.findByUrl("/$generatedUrl")?.let { url ->
            val rawData = s3.readData(url)
            return rawData
        }
        return ResponseEntity.notFound().build()
    }
}
