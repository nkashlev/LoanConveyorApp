package ru.nkashlev.loan_dossier_app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_dossier_app.EmailMessage;
import ru.nkashlev.loan_dossier_app.StatusEnum;

@Service
@RequiredArgsConstructor
public class EmailConsumerService {
    private final EmailService emailService;

    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerService.class);

    @KafkaListener(topics = "${spring.kafka.consumer.topic1}")
    public void consumeMessage(EmailMessage emailMessage) {
        LOGGER.info("Received message: {}", emailMessage);

        String email = emailMessage.getAddress();
        Long applicationId = emailMessage.getApplicationId();
        StatusEnum status = emailMessage.getTheme();


        String text = "Hello, your loan application Nº" + applicationId + " " + status + "!\n" +
                "Now you should finish registration by the following link: http://localhost:8080/swagger-ui/index.html#/Default/dealCalculateApplicationIdPut";

        try {
            emailService.sendEmail(email, "Finish registration", text);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending email: {}", e.getMessage());
        }
    }
}
