package ru.nkashlev.loan_dossier_app.dossier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_dossier_app.dossier.entity.Application;
import ru.nkashlev.loan_dossier_app.dossier.entity.util.EmailMessage;
import ru.nkashlev.loan_dossier_app.dossier.repositories.ApplicationRepository;
import ru.nkashlev.loan_dossier_app.dossier.utils.FindIdByApplication;

import static ru.nkashlev.loan_dossier_app.dossier.model.ApplicationStatusHistoryDTO.StatusEnum.CREDIT_ISSUED;

@RequiredArgsConstructor
@Service
public class EmailConsumerCreditIssued {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerCreditIssued.class);

    private final ObjectMapper jsonMapper;

    private final EmailService emailService;

    private final ApplicationRepository applicationRepository;

    @KafkaListener(topics = "${spring.kafka.consumer.credit-issued}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        EmailMessage emailMessage = jsonMapper.readValue(json, EmailMessage.class);
        LOGGER.info("Consumer JSON mapper from topic - conveyor-credit-issued {} ", emailMessage);
        Application application = new FindIdByApplication(applicationRepository).findIdByApplication(emailMessage.getApplicationId());
        application.setStatus(CREDIT_ISSUED);
        LOGGER.info("Status application is {}", CREDIT_ISSUED);
        sendToEmail(emailMessage);
    }

    private void sendToEmail(EmailMessage emailMessage) {
        String email = emailMessage.getAddress();
        Long applicationId = emailMessage.getApplicationId();
        String subject = "Credit issued!";
        String text = "Hello, credit for your application № " + applicationId + " has already issued.\n"
                + "Money will transfer to your account soon\n" +
                "Thanks!";

        try {
            emailService.sendEmail(email, subject, text);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending email: {} subject {}", e.getMessage(), subject);
        }
        LOGGER.info("Email message sent to {} subject {}", email, subject);
    }
}
