package com.mdud.backendboard24.initializer

import javax.annotation.PostConstruct

abstract class InitializerAutoRunner : Initializer {
    @PostConstruct
    fun addToMainInitializer() {
        MainInitializer.set.add(this)
    }
}