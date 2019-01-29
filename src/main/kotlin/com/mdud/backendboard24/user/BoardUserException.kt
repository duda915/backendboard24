package com.mdud.backendboard24.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class BoardUserException (message: String) : RuntimeException(message)