package me.michelao.shorturl.configuration

import jakarta.servlet.MultipartConfigElement
import jakarta.servlet.ServletRegistration
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class MyWebAppInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun customizeRegistration(registration: ServletRegistration.Dynamic) {
        registration.setMultipartConfig(MultipartConfigElement("/tmp"))
    }

    override fun getRootConfigClasses(): Array<Class<*>>? = null

    override fun getServletConfigClasses(): Array<Class<*>>? = null

    override fun getServletMappings(): Array<String?> = arrayOf("/")
}
