package com.example.shorturl.configuration

import com.example.shorturl.tools.DateConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.TimeUnit

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("classpath:/public/")
            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(DateConverter())
    }
}

