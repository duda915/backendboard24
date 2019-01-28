package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.userauthorities.UserAuthorities
import com.mdud.backendboard24.user.userauthorities.UserAuthoritiesBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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


@Configuration
class BoardUserConfiguration @Autowired constructor(
        private val boardUserRepository: BoardUserRepository,
        private val userAuthoritiesBuilder: UserAuthoritiesBuilder
){

    @Bean
    fun boardUserInitializer() : BoardUserInitializer {
        return BoardUserInitializer(boardUserRepository, userAuthoritiesBuilder)
    }

}