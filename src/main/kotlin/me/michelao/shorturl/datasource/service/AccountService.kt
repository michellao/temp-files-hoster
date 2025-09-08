package me.michelao.shorturl.datasource.service

import me.michelao.shorturl.datasource.Account
import me.michelao.shorturl.datasource.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(private val db: AccountRepository) {
    fun findAccounts(): List<Account> = db.findAll().toList()

    fun findAccountById(id: Int): Account? = db.findByIdOrNull(id)

    fun save(account: Account): Account = db.save(account)
}
