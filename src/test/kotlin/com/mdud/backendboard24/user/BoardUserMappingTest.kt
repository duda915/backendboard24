package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.authority.AuthorityName
import com.mdud.backendboard24.user.userauthorities.UserAuthoritiesBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class BoardUserMappingTest {
    @Autowired
    lateinit var boardUserRepository: BoardUserRepository

    @Autowired
    lateinit var userAuthoritiesBuilder: UserAuthoritiesBuilder

    @Test
    fun save_SaveUserWithoutAuthorities_ShouldSaveUserWithoutAuthorities() {
        var userWithouthAuthorities = BoardUser("test", "test", mutableSetOf())
        userWithouthAuthorities = boardUserRepository.save(userWithouthAuthorities)

        assertNotNull(userWithouthAuthorities.id)
    }

    @Test
    fun save_SaveUserWithAuthorities_ShouldSaveUserWithAuthorites() {
        val normalUserAuthorities = userAuthoritiesBuilder.buildUserAuthority(AuthorityName.USER)
        var normalUser = BoardUser("test", "test", mutableSetOf(normalUserAuthorities))

        normalUser = boardUserRepository.save(normalUser)

        val act = normalUser.userAuthorities.stream().allMatch { it.id != null }

        assertTrue(act)
    }
}