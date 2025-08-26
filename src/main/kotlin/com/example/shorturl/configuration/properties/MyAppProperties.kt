package com.example.shorturl.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("app")
class MyAppProperties {
    lateinit var baseUrl: String
    lateinit var bucketName: String
    val expiration = Expiration()
    class Expiration {
        var minDays: Int = 30
        var maxDays: Int = 365
    }
}
