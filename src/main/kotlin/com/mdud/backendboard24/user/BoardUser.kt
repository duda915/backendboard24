package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.userauthorities.UserAuthorities
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "boarduser")
class BoardUser (
        @Column(name = "username")
        @field:NotEmpty
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
    var password: String = encodePassword(password)
        set(value) {
            field = encodePassword(value)
        }

    companion object {
        val passwordEncoder:BCryptPasswordEncoder = BCryptPasswordEncoder()
    }

    private fun encodePassword(plainPassword: String): String {
        if(plainPassword.isBlank().or(plainPassword.isEmpty())) {
            throw BoardUserException("password cannot be blank or empty")
        }
        return passwordEncoder.encode(plainPassword)
    }
}

