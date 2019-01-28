package com.mdud.backendboard24.user

import org.springframework.data.repository.CrudRepository

interface BoardUserRepository : CrudRepository<BoardUser, Long> {
    fun findByUsername(username: String)
}