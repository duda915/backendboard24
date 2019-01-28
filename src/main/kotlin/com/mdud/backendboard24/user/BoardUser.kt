package com.mdud.backendboard24.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "boarduser")
class BoardUser (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(name = "username")
        val username: String,
        password: String
) {
    @Column(name = "password")
    var password: String = setPassword(password)
        set(value) {
            setPassword(value)
        }

    companion object {
        val passwordEncoder:BCryptPasswordEncoder = BCryptPasswordEncoder()
    }

    private fun setPassword(plainPassword: String): String {
        return passwordEncoder.encode(plainPassword)
    }
}