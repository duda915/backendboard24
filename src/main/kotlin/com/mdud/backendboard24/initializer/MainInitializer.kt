package com.mdud.backendboard24.initializer

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MainInitializer : CommandLineRunner {

    companion object {
        val set = mutableSetOf<Initializer>()
    }
    override fun run(vararg args: String?) {

    }

}

