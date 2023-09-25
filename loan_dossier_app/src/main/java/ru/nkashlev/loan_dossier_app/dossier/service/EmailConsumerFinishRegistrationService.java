package ru.nkashlev.loan_dossier_app.dossier.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_dossier_app.dossier.entity.util.EmailMessage;

import static ru.nkashlev.loan_dossier_app.dossier.model.ApplicationStatusHistoryDTO.StatusEnum;


@RequiredArgsConstructor
@Service
public class EmailConsumerFinishRegistrationService {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerFinishRegistrationService.class);

    private ObjectMapper jsonMapper;
    private EmailService emailService;

    @Autowired
    public EmailConsumerFinishRegistrationService(ObjectMapper jsonMapper, EmailService emailService) {
        this.jsonMapper = jsonMapper;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic1}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        EmailMessage emailMessage = jsonMapper.readValue(json, EmailMessage.class);
        LOGGER.info("Consumer JSON mapper from topic - conveyor-finish-registration {} ", emailMessage);
        sendToEmail(emailMessage);
    }

    private void sendToEmail(EmailMessage emailMessage) {
        String email = emailMessage.getAddress();
        Long applicationId = emailMessage.getApplicationId();
        StatusEnum status = emailMessage.getTheme();
        String subject = "Finish registration";

        String text = String.format("Hello, your loan application Nº %s %s!\n" +
                        "Now you should finish registration by following this link: http://localhost:8080/swagger-ui/index.html#/CalculateDocuments/dealCalculateApplicationIdPut",
                applicationId, status);

        try {
            emailService.sendEmail(email, subject, text);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending email: {} subject {}", e.getMessage(), subject);
        }
        LOGGER.info("Email message sent to {} subject {}", email, subject);
    }
}
