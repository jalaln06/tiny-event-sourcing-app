package ru.quipy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    System.out.println("dsdad")
    runApplication<Application>(*args)
}
