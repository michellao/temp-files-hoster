package com.example.shorturl

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(@RequestParam(defaultValue = "World") name: String) = "Hello fd $name"
}