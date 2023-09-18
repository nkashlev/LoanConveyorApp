package ru.nkashlev.loan_dossier_app.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.topic1}")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
    // TODO: 15.09.2023
}
