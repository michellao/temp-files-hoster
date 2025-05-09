package com.example.shorturl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.assertj.MockMvcTester

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class ShortUrlApplicationTests {
	@Test
	fun testLoadingIndexPage(@Autowired mvc: MockMvcTester) {
		assertThat(mvc.get().uri("/")).hasStatusOk()
	}
}
