package com.example.shorturl.datasource.repository

import com.example.shorturl.datasource.Url
import org.springframework.data.repository.CrudRepository

interface UrlRepository : CrudRepository<Url, Int>