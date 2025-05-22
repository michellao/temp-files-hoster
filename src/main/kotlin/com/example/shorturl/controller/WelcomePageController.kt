package com.example.shorturl.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomePageController {
    @GetMapping("/")
    fun index() = "forward:/resources/index.html"

    @GetMapping("/style.css")
    fun style() = "forward:/resources/style.css"

    @GetMapping("/robots.txt")
    fun robots() = "forward:/resources/robots.txt"
}