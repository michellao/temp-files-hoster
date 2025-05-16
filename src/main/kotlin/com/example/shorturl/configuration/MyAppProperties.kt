package com.example.shorturl.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("app")
class MyAppProperties {
    /**
     * Application's base url
     */
    var baseUrl: String? = null
}