package ru.bobrov.mailsender.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailSender: JavaMailSender
) {

    fun sendMail(email: String, message: String) {
        val mimeMessage = SimpleMailMessage().apply {
            setTo(email)
            subject = "Test"
            text = message
        }

        mailSender.send(mimeMessage)
    }
}