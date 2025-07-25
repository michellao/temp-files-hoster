package com.example.shorturl.tools

import com.example.shorturl.configuration.properties.MyAppProperties
import org.springframework.stereotype.Component
import java.util.Date
import kotlin.math.pow

@Component
class ExpirationCalculator(private val appProperties: MyAppProperties) {
    fun calculateExpiresAt(sizeMebiBytes: Long): Date {
        val now = System.currentTimeMillis()
        val expiration = appProperties.expiration
        val maxSize = 512
        val days = expiration.minDays + (expiration.minDays - expiration.maxDays) * (sizeMebiBytes / maxSize - 1).toDouble().pow(3)
        val daysInMillisecond = (days * 24 * 60 * 60 * 1000).toLong()
        return Date(now + daysInMillisecond)
    }

    fun testExpireUnderLimit(expires: Date): Boolean {
        val now = System.currentTimeMillis()
        val maxOffset = (appProperties.expiration.maxDays * 24 * 60 * 60 * 1000L)
        val dateMaxOffset = Date(now + maxOffset)
        return expires.before(dateMaxOffset)
    }
}