package com.mdud.backendboard24.user

import com.mdud.backendboard24.initializer.InitializerAutoRunner
import com.mdud.backendboard24.user.authority.AuthorityName
import com.mdud.backendboard24.user.userauthorities.UserAuthoritiesBuilder
import org.springframework.transaction.annotation.Transactional

class BoardUserInitializer constructor(
        private val boardUserRepository: BoardUserRepository,
        private val userAuthoritiesBuilder: UserAuthoritiesBuilder
) : InitializerAutoRunner() {

    override fun init() {
        println(DefaultUsers.ADMIN.name.decapitalize())
        println(DefaultUsers.ADMIN.toString().decapitalize())
        boardUserRepository.findByUsername(DefaultUsers.ADMIN.name.toLowerCase()) ?: initUsers()

    }


        private fun initUsers() {
        val userAuthority = userAuthoritiesBuilder.buildUserAuthority(AuthorityName.USER)
        val moderatorAuthority = userAuthoritiesBuilder.buildUserAuthority(AuthorityName.MODERATOR)
        val adminAuthority = userAuthoritiesBuilder.buildUserAuthority(AuthorityName.ADMIN)

        val defaultUserAuthoritiesSet = mutableSetOf(userAuthority)
        val defaultUser = BoardUser("user", "user", defaultUserAuthoritiesSet)

        val defaultModeratorAuthoritiesSet = mutableSetOf(userAuthority, moderatorAuthority)
        val defaultModerator = BoardUser("moderator", "moderator", defaultModeratorAuthoritiesSet)

        val defaultAdminAuthoritiesSet = mutableSetOf(userAuthority, moderatorAuthority, adminAuthority)
        val defaultAdmin = BoardUser("admin", "admin", defaultAdminAuthoritiesSet)

        boardUserRepository.save(defaultUser)
        boardUserRepository.save(defaultModerator)
        boardUserRepository.save(defaultAdmin)
    }

}