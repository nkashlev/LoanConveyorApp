package ru.nkashlev.loan_deal_app.deal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.CreditRepository;
import ru.nkashlev.loan_deal_app.deal.utils.UpdateApplicationStatusHistory;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.CC_APPROVED;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.PREPARE_DOCUMENTS;

@Service
public class CreateDocumentsProducerService extends AbstractProducerService {
    private final String topic;
    private final Logger LOGGER = LoggerFactory.getLogger(CreateDocumentsProducerService.class);
    private final static StatusEnum BEFORE_STATUS = CC_APPROVED;
    private final static StatusEnum AFTER_STATUS = PREPARE_DOCUMENTS;
    private final UpdateApplicationStatusHistory updateApplicationStatusHistory;

    public CreateDocumentsProducerService(ApplicationRepository applicationRepository, CreditRepository creditRepository, KafkaProducer kafkaProducer,
                                          @Value("${spring.kafka.producer.topic3}") String topic, UpdateApplicationStatusHistory updateApplicationStatusHistory) {
        super(applicationRepository, creditRepository, kafkaProducer);
        this.topic = topic;
        this.updateApplicationStatusHistory = updateApplicationStatusHistory;
    }

    @Override
    protected void updateApplication(Application application, Long applicationId) {
        updateApplicationStatusHistory.updateApplicationStatusHistory(application, AFTER_STATUS, AUTOMATIC);
        LOGGER.info("Application for document creation updated with ID: {}", applicationId);
        LOGGER.info("Status application is {}", AFTER_STATUS);
    }

    @Override
    protected String getTopic() {
        return topic;
    }

    @Override
    protected StatusEnum getBeforeStatus() {
        return BEFORE_STATUS;
    }

    @Override
    protected StatusEnum getAfterStatus() {
        return AFTER_STATUS;
    }
}
