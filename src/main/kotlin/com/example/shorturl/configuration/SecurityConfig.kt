package com.example.shorturl.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
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
        users.createUser(user)
        return users
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)
}
