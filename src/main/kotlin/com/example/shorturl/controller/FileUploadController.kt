package com.example.shorturl.controller

import com.example.shorturl.UrlHandler
import com.example.shorturl.datasource.S3ClientData
import com.example.shorturl.datasource.Url
import com.example.shorturl.datasource.service.UrlService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.util.Date
import kotlin.math.pow

@Controller
class FileUploadController(private val service: UrlService, private val s3: S3ClientData) {
    @PostMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun postFile(
        @RequestHeader("User-Agent") userAgent: String?,
        request: HttpServletRequest,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("expires") expires: Date? = null
    ): String {
        if (!file.isEmpty) {
            val contentType = file.contentType ?: MediaType.APPLICATION_OCTET_STREAM.toString()
            val originalFileName = file.originalFilename
            val sizeMebibytes = (file.size / 2.0.pow(20)).toLong()
            var generatedUrl: String
            do {
                generatedUrl = UrlHandler.generatorURL(14)
                val findUrl = service.findByUrl("/$generatedUrl")
            } while (findUrl != null)
            var expiresConverted = Url.calculateExpiresAt(sizeMebibytes)
            if (expires != null) {
                expiresConverted = expires
            }
            val url = Url(
                originalFileName,
                "/$generatedUrl",
                contentType,
                sizeMebibytes,
                request.remoteAddr,
                userAgent,
                expiresConverted
            )
            service.save(url)
            s3.writeData(generatedUrl, file.inputStream, contentType)
            return "/$generatedUrl"
        }
        return "error uploaded"
    }
}