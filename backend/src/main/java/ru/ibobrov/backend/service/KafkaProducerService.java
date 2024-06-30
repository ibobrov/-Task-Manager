package ru.ibobrov.backend.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static ru.ibobrov.backend.config.KafkaConfiguration.WELCOME_LETTER_TOPIC;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWelcomeLetter(String message) {
        kafkaTemplate.send(WELCOME_LETTER_TOPIC, message);
    }
}
