package com.example.shorturl.controller

import com.example.shorturl.UrlHandler
import com.example.shorturl.configuration.properties.MyAppProperties
import com.example.shorturl.datasource.MimeType
import com.example.shorturl.datasource.S3ClientData
import com.example.shorturl.datasource.Url
import com.example.shorturl.datasource.service.UrlService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.util.Date
import java.util.logging.Logger
import kotlin.math.pow

@Controller
class FileUploadController(
    private val service: UrlService,
    private val s3: S3ClientData,
    private val appProperties: MyAppProperties,
    private val expirationCalculator: ExpirationCalculator,
) {
    private val logger = Logger.getLogger(this.javaClass.name)
    fun readRealIp(request: HttpServletRequest): String {
        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null) {
            ip = request.getHeader("x-real-ip")
        }
        if (ip == null) {
            ip = request.remoteAddr
        }
        return ip
    }

    @PostMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun postFile(
        @RequestHeader("User-Agent") userAgent: String?,
        request: HttpServletRequest,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("expires") expires: Date? = null
    ): ResponseEntity<String> {
        if (!file.isEmpty) {
            val contentType = file.contentType ?: MediaType.APPLICATION_OCTET_STREAM.toString()
            val originalFileName = file.originalFilename
            val sizeMebibytes = (file.size / 2.0.pow(20)).toLong()
            var generatedUrl: String
            do {
                generatedUrl = UrlHandler.generatorURL(14)
                val findUrl = service.findByUrl("/$generatedUrl")
            } while (findUrl != null)
            var expiresConverted = expirationCalculator.calculateExpiresAt(sizeMebibytes)
            if (expires != null) {
                val now = System.currentTimeMillis()
                val maxOffset = (appProperties.expiration.maxDays * 24 * 60 * 60 * 1000L)
                val dateMaxOffset = Date(now + maxOffset)
                if (expires.before(dateMaxOffset)) {
                    expiresConverted = expires
                } else {
                    return ResponseEntity.badRequest().body("The expiry date has exceeded the maximum age")
                }
            }
            val realIp = readRealIp(request)
            logger.info("IP: $realIp, Upload filename: $originalFileName")
            val url = Url(
                originalFileName,
                "/$generatedUrl",
                MimeType.fromValue(contentType),
                sizeMebibytes,
                realIp,
                userAgent,
                expiresConverted
            )
            service.save(url)
            s3.writeData(generatedUrl, file.inputStream, contentType)
            return ResponseEntity.ok("${appProperties.baseUrl ?: ""}/$generatedUrl")
        }
        return ResponseEntity.badRequest().body("error uploaded")
    }
}