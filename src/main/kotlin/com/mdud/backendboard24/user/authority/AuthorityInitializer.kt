package com.mdud.backendboard24.user.authority

import com.mdud.backendboard24.initializer.InitializerAutoRunner
import org.springframework.beans.factory.annotation.Autowired

class AuthorityInitializer constructor(
        private val authorityRepository: AuthorityRepository
) : InitializerAutoRunner() {

        override fun init() {
                authorityRepository.findByAuthorityName(AuthorityName.ADMIN) ?: initAuthorities()
        }

        private fun initAuthorities() {
                AuthorityName.values().forEach {
                        authorityRepository.save(Authority(it))
                }
        }
}