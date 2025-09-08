package me.michelao.shorturl.datasource.repository

import me.michelao.shorturl.datasource.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Int>
