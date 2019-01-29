package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.userauthorities.UserAuthoritiesBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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