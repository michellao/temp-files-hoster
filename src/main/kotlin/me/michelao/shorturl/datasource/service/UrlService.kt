package me.michelao.shorturl.datasource.service

import me.michelao.shorturl.datasource.Url
import me.michelao.shorturl.datasource.repository.UrlRepository
import org.springframework.stereotype.Service

@Service
class UrlService(private val db: UrlRepository) {
    fun save(url: Url): Url = db.save(url)
    fun findByUrl(urlPath: String): Url? = db.findByGeneratedUrl(urlPath)
    fun findExpiredUrls(): List<Url> = db.findExpiredUrls()
    fun delete(url: Url) = db.delete(url)
}
