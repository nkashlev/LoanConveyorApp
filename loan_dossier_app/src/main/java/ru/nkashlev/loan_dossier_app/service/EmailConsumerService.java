package ru.nkashlev.loan_dossier_app.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_dossier_app.dto.EmailMessage;
import ru.nkashlev.loan_dossier_app.dto.StatusEnum;


@RequiredArgsConstructor
@Service
public class EmailConsumerService {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerService.class);

    private ObjectMapper jsonMapper;
    private EmailService emailService;

    @Autowired
    public EmailConsumerService(ObjectMapper jsonMapper, EmailService emailService) {
        this.jsonMapper = jsonMapper;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic1}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        EmailMessage emailMessage = jsonMapper.readValue(json, EmailMessage.class);
        LOGGER.info("Consumer JSON mapper {} ", emailMessage);

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
        LOGGER.info("Email message sent to {} ", email);
    }
}
