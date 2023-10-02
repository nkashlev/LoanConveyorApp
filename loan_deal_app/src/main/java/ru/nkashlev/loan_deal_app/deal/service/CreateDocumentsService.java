package ru.nkashlev.loan_deal_app.deal.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.util.EmailMessage;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.utils.FindIdByApplication;
import ru.nkashlev.loan_deal_app.deal.utils.UpdateApplicationStatusHistory;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.*;

@Service
@RequiredArgsConstructor
public class CreateDocumentsService {

    private final ApplicationRepository applicationRepository;

    private final KafkaProducer kafkaProducer;

    @Value("${spring.kafka.producer.topic2}")
    private String topic;

    private final Logger LOGGER = LoggerFactory.getLogger(CreateDocumentsService.class);

    public void sendMessageToKafka(Long applicationId) throws ResourceNotFoundException {
        Application application = new FindIdByApplication(applicationRepository).findIdByApplication(applicationId);
        if (application.getStatus().equals(CC_APPROVED)) {
            new UpdateApplicationStatusHistory(applicationRepository).updateApplicationStatusHistory(application, PREPARE_DOCUMENTS, AUTOMATIC);
            LOGGER.info("Application for document creation updated with ID: {}", applicationId);

            EmailMessage message = new EmailMessage(application.getClient().getEmail(), application.getApplicationId(), PREPARE_DOCUMENTS);
            kafkaProducer.sendMessage(topic, message);
            LOGGER.info("Message sent to kafka with topic - conveyor-create-documents");
        } else {
            LOGGER.info("Application does not exist or the status is incorrect");
        }
    }
}
