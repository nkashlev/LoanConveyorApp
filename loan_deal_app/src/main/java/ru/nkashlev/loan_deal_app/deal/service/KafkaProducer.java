package ru.nkashlev.loan_deal_app.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.util.EmailMessage;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, EmailMessage message) {
        kafkaTemplate.send(topic, message);
    }
}
