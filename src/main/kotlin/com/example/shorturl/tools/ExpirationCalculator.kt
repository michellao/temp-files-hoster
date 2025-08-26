package com.example.shorturl.tools

import com.example.shorturl.configuration.properties.MyAppProperties
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.pow

@Component
class ExpirationCalculator(private val appProperties: MyAppProperties) {
    fun calculateExpiresAt(sizeMebiBytes: Double): Date {
        val now = System.currentTimeMillis()
        val expiration = appProperties.expiration
        val maxSize = 512
        val days = expiration.minDays + (expiration.minDays - expiration.maxDays) * (sizeMebiBytes / maxSize - 1).pow(3)
        val daysInMillisecond = TimeUnit.DAYS.toMillis(days.toLong())
        return Date(now + daysInMillisecond)
    }

    fun testExpireUnderLimit(expires: Date): Boolean {
        val now = System.currentTimeMillis()
        val maxOffset = TimeUnit.DAYS.toMillis(appProperties.expiration.maxDays.toLong())
        val dateMaxOffset = Date(now + maxOffset)
        return expires.before(dateMaxOffset)
    }
}
