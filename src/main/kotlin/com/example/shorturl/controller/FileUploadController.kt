package com.example.shorturl.controller

import com.example.shorturl.UrlHandler
import com.example.shorturl.datasource.S3ClientData
import com.example.shorturl.datasource.Url
import com.example.shorturl.datasource.service.UrlService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import kotlin.math.pow

@Controller
class FileUploadController(private val service: UrlService, private val s3: S3ClientData) {
    @PostMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun postFile(@RequestParam("file") file: MultipartFile): String {
        if (!file.isEmpty) {
            val contentType = file.contentType ?: MediaType.APPLICATION_OCTET_STREAM.toString()
            val originalFileName = file.originalFilename
            val sizeMebibytes = (file.size / 2.0.pow(20)).toLong()
            var generatedUrl: String
            do {
                generatedUrl = UrlHandler.generatorURL(14)
                val findUrl = service.findByUrl("/$generatedUrl")
            } while (findUrl != null)
            val url = Url(
                originalFileName,
                "/$generatedUrl",
                contentType,
                sizeMebibytes,
            )
            println("Url: $url")
            service.save(url)
            s3.writeData(generatedUrl, file.inputStream, contentType)
            return "/$generatedUrl"
        }
        return "error uploaded"
    }
}