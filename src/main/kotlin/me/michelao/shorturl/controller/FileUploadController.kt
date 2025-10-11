package me.michelao.shorturl.controller

import me.michelao.shorturl.UrlHandler
import me.michelao.shorturl.configuration.properties.MyAppProperties
import me.michelao.shorturl.datasource.MimeType
import me.michelao.shorturl.datasource.S3ClientData
import me.michelao.shorturl.datasource.Url
import me.michelao.shorturl.datasource.service.UrlService
import me.michelao.shorturl.tools.ExpirationCalculator
import jakarta.servlet.http.HttpServletRequest
import me.michelao.shorturl.tools.WebTools
import org.apache.tika.Tika
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
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

    @PostMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    @CrossOrigin(exposedHeaders = ["X-Token"])
    @ResponseBody
    fun postFile(
        @RequestHeader("User-Agent") userAgent: String?,
        request: HttpServletRequest,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("expires") expires: Date? = null
    ): ResponseEntity<String> {
        if (!file.isEmpty) {
            val tika = Tika()
            val contentType = tika.detect(file.inputStream)
            val originalFileName = file.originalFilename
            val sizeMebibytes = file.size / 2.0.pow(20)
            var generatedUrl: String
            do {
                generatedUrl = UrlHandler.generatorURL(14)
                val findUrl = service.findByUrl("/$generatedUrl")
            } while (findUrl != null)
            var expiresConverted = expirationCalculator.calculateExpiresAt(sizeMebibytes)
            if (expires != null) {
                if (expirationCalculator.testExpireUnderLimit(expires)) {
                    expiresConverted = expires
                } else {
                    return ResponseEntity.badRequest().body("The expiry date has exceeded the maximum age")
                }
            }
            val realIp = WebTools.readRealIp(request)
            val token = UrlHandler.generatorToken()
            logger.info("IP: $realIp, Upload filename: $originalFileName")
            logger.info("Content type detected: $contentType")
            val url = Url(
                originalFileName,
                "/$generatedUrl",
                MimeType.fromValue(contentType),
                file.size,
                token,
                realIp,
                userAgent,
                expiresConverted
            )
            service.save(url)
            s3.writeData(generatedUrl, file.inputStream, file.size, contentType)
            return ResponseEntity
                .ok()
                .headers {
                    it.set("X-Token", token)
                }
                .body("${appProperties.baseUrl}/${generatedUrl}")
        }
        return ResponseEntity.badRequest().body("File to upload cannot be empty")
    }
}
