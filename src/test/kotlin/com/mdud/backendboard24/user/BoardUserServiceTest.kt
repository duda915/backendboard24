package com.mdud.backendboard24.user

import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import javax.validation.ConstraintViolationException

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class BoardUserServiceTest {

    @Autowired
    lateinit var boardUserService: BoardUserService

    @Autowired
    lateinit var boardUserRepository: BoardUserRepository

    @Test
    fun getUser_GetExistingUser_ShouldReturnUser() {
        val admin = boardUserService.getUser("admin")
        val adminFromRepository = boardUserRepository.findByUsername("admin")

        assertEquals(adminFromRepository, admin)
    }

    @Test(expected = BoardUserException::class)
    fun getUser_GetNonExistingUser_ShouldThrowException() {
        boardUserService.getUser("nonexistinguser")
    }

    @Test
    fun addUser_AddNonExistingUser_ShouldAddUser() {
        val newUserDTO = BoardUserDTO("newuser", "newuser")
        val newuser = boardUserService.addUser(newUserDTO)
        val expectedNewUser = boardUserRepository.findByUsername(newuser.username)

        assertEquals(expectedNewUser, newuser)
    }

    @Test(expected = BoardUserException::class)
    fun addUser_AddExistingUser_ShouldThrowException() {
        val newUserDTO = BoardUserDTO("admin", "xxx")
        boardUserService.addUser(newUserDTO)
    }

    @Test(expected = BoardUserException::class)
    fun removeUser_RemoveExistingUser_ShouldRemoveUser() {
        boardUserService.removeUser("admin")
        boardUserService.getUser("admin")
    }

    @Test(expected = BoardUserException::class)
    fun removeUser_RemoveNonExistingUser() {
        boardUserService.removeUser("nonexistinguser")
    }

    @Test
    fun changePassword_ChangeExistingUserPassword_ShouldChangeUserPassword() {
        val userDTO = BoardUserDTO("admin", "newpassword")
        boardUserService.changePassword(userDTO)
        val expectedAdmin = boardUserService.getUser("admin")

        assertTrue(BoardUser.passwordEncoder.matches(userDTO.plainPassword, expectedAdmin.password))
    }

    @Test(expected = BoardUserException::class)
    fun changePassword_ChangeNonExistingUserPassword_ShouldThrowException() {
        val userDTO = BoardUserDTO("nonexistinguser", "newpass")
        boardUserService.changePassword(userDTO)
    }

    @Test(expected = ConstraintViolationException::class)
    fun addUser_AddUserWithEmptyUsername_ShouldThrowException() {
        val userDTO = BoardUserDTO("", "asd")
        boardUserService.addUser(userDTO)
    }

    @Test(expected = BoardUserException::class)
    fun addUser_AddUserWithEmptyPassword_ShouldThrowException() {
        val userDTO = BoardUserDTO("nonexistinguser", "")
        boardUserService.addUser(userDTO)
    }
}