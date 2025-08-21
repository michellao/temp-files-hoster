package com.example.shorturl.controller

import com.example.shorturl.datasource.AboutApp
import org.springframework.boot.info.GitProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApplicationInfo(
    private val gitProperties: GitProperties
) {
    @GetMapping("/app/version")
    fun getVersion(): AboutApp {
        val aboutApp = AboutApp(
            gitProperties.branch,
            gitProperties.shortCommitId,
            gitProperties.commitId,
        )
        return aboutApp
    }
}