package com.example.shorturl.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomePageController {
    @GetMapping("/")
    fun index() = "forward:/index.html"
}
