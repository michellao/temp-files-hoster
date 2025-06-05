package com.example.shorturl.configuration

import com.example.shorturl.configuration.properties.MyAppProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import java.util.logging.Logger
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
            httpBasic { }
            cors { disable() }
            csrf { disable() }
        }
        return http.build()
    }

    @Bean
    fun users(dataSource: DataSource): UserDetailsManager {
        val encoder = passwordEncoder()
        val user = User.builder()
            .username("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .build()
        val users = JdbcUserDetailsManager(dataSource)
        try {
            users.createUser(user)
        } catch (e: DuplicateKeyException) {
            Logger.getLogger(MyAppProperties::class.java.name).info("Initialize a default user already exists")
        }
        return users
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)
}
