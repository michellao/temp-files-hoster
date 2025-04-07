package com.example.shorturl.controller

import com.example.shorturl.datasource.repository.UrlRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.logging.Logger

@RestController
class FileUploadController(private val urlRepository: UrlRepository) {
    @PostMapping("/")
    fun index(@RequestParam("file") file: MultipartFile): String {
        if (!file.isEmpty) {
            val contentType = file.contentType
            Logger.getAnonymousLogger().info("contentType: $contentType")

            return "uploadSuccess"
        }
        return "error uploaded"
    }
}