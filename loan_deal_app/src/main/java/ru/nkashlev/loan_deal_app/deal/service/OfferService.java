package ru.nkashlev.loan_deal_app.deal.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.util.EmailMessage;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.utils.FindIdByApplication;
import ru.nkashlev.loan_deal_app.deal.utils.UpdateApplicationStatusHistory;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.APPROVED;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final ApplicationRepository applicationRepository;

    private final KafkaProducer kafkaProducer;

    @Value("${spring.kafka.producer.finish-registration}")
    private String topic;

    private final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    private final UpdateApplicationStatusHistory updateApplicationStatusHistory;

    public void updateApplication(LoanOfferDTO request) throws ResourceNotFoundException {
        Application application = new FindIdByApplication(applicationRepository).findIdByApplication(request.getApplicationId());
        application.setAppliedOffer(request);
        updateApplicationStatusHistory.updateApplicationStatusHistory(application, APPROVED, AUTOMATIC);
        LOGGER.info("Application updated with ID: {}", request.getApplicationId());

        EmailMessage message = new EmailMessage(application.getClient().getEmail(), application.getApplicationId(), APPROVED);
        kafkaProducer.sendMessage(topic, message);
        LOGGER.info("Message sent to kafka with topic - conveyor-finish-registration");
    }
}

