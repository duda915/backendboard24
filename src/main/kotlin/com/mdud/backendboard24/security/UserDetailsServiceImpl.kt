package com.mdud.backendboard24.security

import com.mdud.backendboard24.user.BoardUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl @Autowired constructor(
        private val boardUserRepository: BoardUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(p0: String?): UserDetails {
        p0 ?: throw LoginException("username $p0 is null")
        val boardUser = boardUserRepository.findByUsername(p0) ?: throw UsernameNotFoundException("user $p0 not found")

        val authorities = mutableListOf<GrantedAuthority>()
        boardUser.userAuthorities.forEach { userAuthorities -> authorities.add(SimpleGrantedAuthority(
                userAuthorities.authority.authorityName.name
        )) }

        return User(boardUser.username, boardUser.password, authorities)
    }
}

