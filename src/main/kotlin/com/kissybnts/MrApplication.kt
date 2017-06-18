package com.kissybnts

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MrApplication

fun main(args: Array<String>) {
    SpringApplication.run(MrApplication::class.java, *args)
}
