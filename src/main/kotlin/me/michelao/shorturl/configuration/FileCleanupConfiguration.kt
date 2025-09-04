package me.michelao.shorturl.configuration

import me.michelao.shorturl.datasource.S3ClientData
import me.michelao.shorturl.datasource.service.UrlService
import org.apache.commons.logging.LogFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class FileCleanupConfiguration(private val s3Client: S3ClientData, private val urlService: UrlService) {
    private val log = LogFactory.getLog(javaClass)

    @Scheduled(cron = "0 0 * * * *")
    fun deleteExpiredFiles() {
        log.info("Deleting expired files")
        val expiredUrls = urlService.findExpiredUrls()
        expiredUrls.forEach { url ->
            val pathKey = url.urlPath
            s3Client.deleteData(pathKey)
            urlService.delete(url)
        }
    }
}
