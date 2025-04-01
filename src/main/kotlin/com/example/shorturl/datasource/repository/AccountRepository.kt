package com.example.shorturl.datasource.repository

import com.example.shorturl.datasource.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Int>