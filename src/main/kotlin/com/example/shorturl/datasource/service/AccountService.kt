package com.example.shorturl.datasource.service

import com.example.shorturl.datasource.Account
import com.example.shorturl.datasource.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(private val db: AccountRepository) {
    fun findAccounts(): List<Account> = db.findAll().toList()

    fun findAccountById(id: Int): Account? = db.findByIdOrNull(id)

    fun save(account: Account): Account = db.save(account)
}