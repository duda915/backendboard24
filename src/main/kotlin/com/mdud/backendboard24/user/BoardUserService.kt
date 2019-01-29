package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.authority.AuthorityName
import com.mdud.backendboard24.user.userauthorities.UserAuthoritiesBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardUserService @Autowired constructor(
        private val boardUserRepository: BoardUserRepository,
        private val userAuthoritiesBuilder: UserAuthoritiesBuilder
) {

    private fun throwIfUserNotExists(username: String) {
        boardUserRepository.findByUsername(username) ?: throw BoardUserException("user $username not exists")
    }

    private fun throwIfUserExists(username: String) {
        boardUserRepository.findByUsername(username) ?: return
        throw BoardUserException("user $username already exists")
    }

    fun getUser(username: String) : BoardUser {
        throwIfUserNotExists(username)

        return boardUserRepository.findByUsername(username)!!
    }

    fun addUser(boardUserDTO: BoardUserDTO) : BoardUser {
        throwIfUserExists(boardUserDTO.username)

        val authoritiesSet = mutableSetOf(userAuthoritiesBuilder.buildUserAuthority(AuthorityName.USER))
        val boardUser = BoardUser(boardUserDTO.username, boardUserDTO.plainPassword, authoritiesSet)

        return boardUserRepository.save(boardUser)
    }

    fun removeUser(username: String) {
        throwIfUserNotExists(username)

        val boardUser = boardUserRepository.findByUsername(username)!!
        boardUserRepository.delete(boardUser)
    }

    fun setUserAuthorities(username: String, authoritiesSet: MutableSet<AuthorityName>) : BoardUser {
        throwIfUserNotExists(username)

        val boardUser = getUser(username)
        boardUser.userAuthorities.removeAll { true }
        authoritiesSet.forEach {
            val userAuthority = userAuthoritiesBuilder.buildUserAuthority(it)
            boardUser.userAuthorities.add(userAuthority)
        }

        return boardUserRepository.save(boardUser)
    }

    fun changePassword(boardUserDTO: BoardUserDTO) {
        throwIfUserNotExists(boardUserDTO.username)

        val boardUser = boardUserRepository.findByUsername(boardUserDTO.username)!!
        boardUser.password = boardUserDTO.plainPassword

        boardUserRepository.save(boardUser)
    }

}