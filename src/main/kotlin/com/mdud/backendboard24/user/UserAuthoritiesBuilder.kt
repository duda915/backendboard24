package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.authority.AuthorityName
import com.mdud.backendboard24.user.authority.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired

class UserAuthoritiesBuilder @Autowired constructor(
        private val authorityRepository: AuthorityRepository
){
    fun buildUserAuthority(authorityName: AuthorityName) : UserAuthorities {
        val authority = authorityRepository.findByAuthorityName(authorityName)
        return UserAuthorities(authority)
    }
}