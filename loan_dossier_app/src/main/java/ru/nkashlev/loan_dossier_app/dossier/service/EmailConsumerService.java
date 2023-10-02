package ru.nkashlev.loan_dossier_app.dossier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_dossier_app.dossier.entity.util.EmailMessage;
@RequiredArgsConstructor
@Service
public class EmailConsumerService {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerService.class);

    private final ObjectMapper jsonMapper;

    private final EmailService emailService;

    @KafkaListener(topics = "${spring.kafka.consumer.create-documents}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        EmailMessage emailMessage = jsonMapper.readValue(json, EmailMessage.class);
        LOGGER.info("Consumer JSON mapper from topic - conveyor-create-documents {} ", emailMessage);
        sendToEmail(emailMessage);
    }
    private void sendToEmail(EmailMessage emailMessage) {
        String text = "Hello your loan application № " + emailMessage.getApplicationId() + " passed all checks!\n"
                + "Now should send creating documents request by following link: http://localhost:8080/swagger-ui/index.html#/CreateDocuments/createDocuments";
        String email = emailMessage.getAddress();
        String subject = "Create documents for your loan application";
        try {
            emailService.sendEmail(email, subject, text);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending email: {} subject: {}", e.getMessage(), subject);
        }
        LOGGER.info("Email message sent to {} subject: {}", email, subject);
    }
}
