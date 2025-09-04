package me.michelao.shorturl.controller

import me.michelao.shorturl.datasource.S3ClientData
import me.michelao.shorturl.datasource.service.UrlService
import me.michelao.shorturl.tools.ExpirationCalculator
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Date
import java.util.logging.Logger

@Controller
class UrlController(
    private val service: UrlService,
    private val s3: S3ClientData,
    private val expirationCalculator: ExpirationCalculator,
) {
    private val logger = Logger.getLogger(this.javaClass.name)

    @GetMapping("/{generatedUrl}", produces = [MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getFile(@PathVariable("generatedUrl") generatedUrl: String): ResponseEntity<ByteArray> {
        logger.info("Try access to: $generatedUrl")
        service.findByUrl("/$generatedUrl")?.let { url ->
            val rawData = s3.readData(url)
            return rawData
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/{generatedUrl}")
    fun manageFile(
        @PathVariable("generatedUrl") generatedUrl: String,
        @RequestParam("token") token: String,
        @RequestParam("delete") delete: String? = null,
        @RequestParam("expires") expires: Date? = null
    ): ResponseEntity<String> {
        logger.info("Try deleted: $generatedUrl")
        service.findByUrl("/$generatedUrl")?.let { url ->
            if (token == url.token) {
                if (delete != null) {
                    val urlPath = url.urlPath
                    s3.deleteData(urlPath)
                    service.delete(url)
                    return ResponseEntity.accepted().build()
                }

                if (expires != null) {
                    if (expirationCalculator.testExpireUnderLimit(expires)) {
                        url.expiresAt = expires
                        service.save(url)
                        return ResponseEntity.accepted().build()
                    } else {
                        return ResponseEntity.badRequest().body("The expiry date has exceeded the maximum age")
                    }
                }
            }
        }
        return ResponseEntity.notFound().build()
    }
}
