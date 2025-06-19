package com.example.shorturl

import com.example.shorturl.configuration.properties.MyAppProperties
import jakarta.servlet.ServletContext
import org.hamcrest.core.StringStartsWith
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FileUploadTests(@Autowired private val myApp: MyAppProperties) {
    @Autowired
    private lateinit var servletContext: ServletContext
    @Test
    fun testUpload(@Autowired mvc: MockMvc) {
        val file = MockMultipartFile(
            "file",
            "fileTest.txt",
            "text/plain",
            "Hello world".toByteArray()
        )
        mvc.perform {
            multipart("/")
                .file(file)
                .buildRequest(servletContext)
        }
            .andExpect(status().isOk)
    }

    @Test
    fun testUploadExceedMaxAge(@Autowired mockMvc: MockMvc) {
        val file = MockMultipartFile(
            "file",
            "fileTest.txt",
            "text/plain",
            "Hello world".toByteArray()
        )
        mockMvc.perform {
            multipart("/")
                .file(file)
                .param("expires", "${(myApp.expiration.maxDays + 1) * 24 * 60 * 60}s")
                .buildRequest(servletContext)
        }
            .andExpect(status().is4xxClientError)
    }
}