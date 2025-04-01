package com.example.shorturl

import com.example.shorturl.datasource.Account
import com.example.shorturl.datasource.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/accounts")
class AccountController(private val service: AccountService) {
    @GetMapping
    fun listAccounts() = service.findAccounts()

    @PostMapping
    fun post(@RequestBody account: Account): ResponseEntity<Account?> {
        val savedAccount = service.save(account)
        return ResponseEntity.created(URI.create("/${savedAccount.id}")).body(savedAccount)
    }
}