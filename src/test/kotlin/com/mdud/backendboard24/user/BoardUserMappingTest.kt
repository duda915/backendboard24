package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.userauthorities.UserAuthoritiesBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional


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

    }
}