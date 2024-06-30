package ru.bobrov.mailsendler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MailSendlerApplication

fun main(args: Array<String>) {
    runApplication<MailSendlerApplication>(*args)
}
