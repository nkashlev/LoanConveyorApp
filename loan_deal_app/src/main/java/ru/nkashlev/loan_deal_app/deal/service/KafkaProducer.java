package ru.nkashlev.loan_deal_app.deal.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.util.EmailMessage;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, EmailMessage message) {
        kafkaTemplate.send(topic, message);
        LOGGER.info("Message sent");
    }
}
