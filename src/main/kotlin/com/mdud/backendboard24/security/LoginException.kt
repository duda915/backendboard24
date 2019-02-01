package com.mdud.backendboard24.security

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class LoginException (message: String) : RuntimeException(message)