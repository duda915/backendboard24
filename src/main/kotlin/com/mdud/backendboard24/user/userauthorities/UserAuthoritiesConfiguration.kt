package com.mdud.backendboard24.user.userauthorities

import com.mdud.backendboard24.user.authority.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserAuthoritiesConfiguration @Autowired constructor(
        private val authorityRepository: AuthorityRepository
){

    @Bean
    fun userAuthoritiesBuilder() : UserAuthoritiesBuilder {
        return UserAuthoritiesBuilder(authorityRepository)
    }
}