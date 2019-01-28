package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.authority.Authority
import com.mdud.backendboard24.user.authority.AuthorityName
import com.mdud.backendboard24.user.authority.AuthorityRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class AuthorityMappingTest @Autowired constructor(
        val authorityRepository: AuthorityRepository
) {
    @Test(expected = Exception::class)
    fun save_TryToSaveSameAuthority_ShouldThrowException() {
        var authority = Authority(AuthorityName.ADMIN)
        authority = authorityRepository.save(authority)
    }
}