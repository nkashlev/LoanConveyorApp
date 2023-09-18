package ru.nkashlev.loan_deal_app.deal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    public void sendMessage(String topic, String key, String message) {
//        kafkaTemplate.send(topic, key, message);
//        LOGGER.info("Message sent");
//    }

//        public void sendMessage(String topic, EmailMessage message) {
//        kafkaTemplate.send(topic, message);
//        LOGGER.info("Message sent");
//
//    }
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        LOGGER.info("Message sent");
    }

}
