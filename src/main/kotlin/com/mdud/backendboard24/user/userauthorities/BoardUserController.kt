package com.mdud.backendboard24.user.userauthorities

import com.mdud.backendboard24.user.BoardUser
import com.mdud.backendboard24.user.BoardUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/user")
class BoardUserController @Autowired constructor(
        val boardUserService: BoardUserService
){

    @GetMapping
    @Transactional
    fun getLoggedUser(principal: Principal) : BoardUser {
        return boardUserService.getUser(principal.name)
    }
}
