package com.mdud.backendboard24.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "boarduser")
class BoardUser (
        @Column(name = "username")
        val username: String,
        password: String,

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn(name = "user_id")
        val userAuthorities: MutableSet<UserAuthorities>

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

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


data class UserAuthorities (
        @ManyToOne(cascade = [CascadeType.MERGE])
        @JoinColumn(name = "authority_id")
        val authority: Authority
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

