package com.example.shorturl

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer

class Initializer : AbstractHttpSessionApplicationInitializer(Config::class.java)