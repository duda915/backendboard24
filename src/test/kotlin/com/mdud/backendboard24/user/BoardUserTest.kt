package com.mdud.backendboard24.user

import org.junit.Assert.*
import org.junit.Test

class BoardUserTest {

    @Test
    fun constructor_TestPasswordEncryption_CreatingObjectShouldEncryptPassword() {
        val password = "test"
        val user = BoardUser( "username", password, mutableSetOf())
        assertNotEquals(password, user.password)
        println(user.password)
    }
}