package com.example.shorturl.controller

import com.example.shorturl.datasource.S3ClientData
import com.example.shorturl.datasource.repository.UrlRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.util.logging.Logger

@Controller
class FileUploadController(private val urlRepository: UrlRepository, private val s3: S3ClientData) {
    @PostMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun index(@RequestParam("file") file: MultipartFile): String {
        if (!file.isEmpty) {
            val contentType = file.contentType ?: MediaType.APPLICATION_OCTET_STREAM.toString()
            Logger.getAnonymousLogger().info("contentType: $contentType")
            s3.writeData("test", file.inputStream, contentType)
            return "uploadSuccess"
        }
        return "error uploaded"
    }
    @GetMapping("/", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun getDataTest(): ResponseEntity<ByteArray> {
        val inputStream = s3.readData("test")
        return inputStream
    }
}