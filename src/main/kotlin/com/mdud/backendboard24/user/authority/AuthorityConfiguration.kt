package com.mdud.backendboard24.user.authority

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthorityConfiguration @Autowired constructor(
        private val authorityRepository: AuthorityRepository
) {
        @Bean
        fun authorityInitializer() : AuthorityInitializer {
                return AuthorityInitializer(authorityRepository)
        }
}