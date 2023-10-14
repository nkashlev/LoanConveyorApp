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
import ru.nkashlev.loan_dossier_app.dossier.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_dossier_app.dossier.utils.ApplicationUtil;

@RequiredArgsConstructor
@Service
public class EmailConsumerSendCodeService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerSendCodeService.class);

    private final ObjectMapper jsonMapper;

    private final EmailService emailService;

    private final ApplicationUtil applicationUtil;

    @KafkaListener(topics = "${spring.kafka.consumer.send-ses}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        EmailMessage emailMessage = jsonMapper.readValue(json, EmailMessage.class);
        LOGGER.info("Consumer JSON mapper from topic - conveyor-send-ses {} ", emailMessage);
        sendToEmail(emailMessage);
    }

    private void sendToEmail(EmailMessage emailMessage) throws ResourceNotFoundException {
        Application application = applicationUtil.findApplicationById(emailMessage.getApplicationId());
        Long code = application.getSesCode();
        String email = emailMessage.getAddress();
        Long applicationId = emailMessage.getApplicationId();
        String subject = "Your simple electric sing";

        String text = "Hello, here your simple electric sing code " + code + " for loan application № " + applicationId + ".\n"
                + "Now should send this code to: http://localhost:9090/swagger-ui/index.html#/SendSesCode/sendSesCode";

        try {
            emailService.sendEmail(email, subject, text);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending email: {} subject {}", e.getMessage(), subject);
        }
        LOGGER.info("Email message sent to {} subject {}", email, subject);
    }
}
