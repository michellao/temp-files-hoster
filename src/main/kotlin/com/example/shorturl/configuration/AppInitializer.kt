package com.example.shorturl.configuration

import jakarta.servlet.MultipartConfigElement
import jakarta.servlet.ServletRegistration
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class AppInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getServletMappings(): Array<out String?> = arrayOf()

    override fun customizeRegistration(registration: ServletRegistration.Dynamic) {
        registration.setMultipartConfig(MultipartConfigElement("/tmp"))
    }

    override fun getRootConfigClasses(): Array<out Class<*>?>? = arrayOf()

    override fun getServletConfigClasses(): Array<out Class<*>?>? = arrayOf()
}