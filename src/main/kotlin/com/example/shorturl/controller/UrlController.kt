package com.example.shorturl.controller

import com.example.shorturl.datasource.S3ClientData
import com.example.shorturl.datasource.service.UrlService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class UrlController(private val service: UrlService, private val s3: S3ClientData) {
    @GetMapping("/")
    fun index() = "forward:/resources/index.html"

    @GetMapping("/style.css")
    fun style() = "forward:/resources/style.css"

    @GetMapping("/robots.txt")
    fun robots() = "forward:/robots.txt"

    @GetMapping("/{generatedUrl}", produces = [MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getFile(@PathVariable("generatedUrl") generatedUrl: String): ResponseEntity<ByteArray> {
        println("Generated url: $generatedUrl")
        service.findByUrl("/$generatedUrl")?.let { url ->
            val rawData = s3.readData(url)
            return rawData
        }
        return ResponseEntity.notFound().build()
    }
}
