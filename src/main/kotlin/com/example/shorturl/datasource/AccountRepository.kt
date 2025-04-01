package com.example.shorturl.datasource

import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Int>