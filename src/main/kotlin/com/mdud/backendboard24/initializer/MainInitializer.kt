package com.mdud.backendboard24.initializer

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class MainInitializer : CommandLineRunner {

    companion object {
        val set = mutableSetOf<Initializer>()
    }
    override fun run(vararg args: String?) {
        set.forEach { it.init() }
    }

}

