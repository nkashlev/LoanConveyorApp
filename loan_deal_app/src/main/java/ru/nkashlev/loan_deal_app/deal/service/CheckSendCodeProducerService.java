package ru.nkashlev.loan_deal_app.deal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.Credit;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.CreditRepository;
import ru.nkashlev.loan_deal_app.deal.utils.UpdateApplicationStatusHistory;

import static ru.nkashlev.loan_deal_app.deal.entity.util.CreditStatus.ISSUED;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.*;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.*;


@Service
public class CheckSendCodeProducerService extends AbstractProducerService {
    private final String topic;
    private final Logger LOGGER = LoggerFactory.getLogger(CheckSendCodeProducerService.class);
    private final StatusEnum BEFORE_STATUS = DOCUMENT_CREATED;
    private final static StatusEnum AFTER_STATUS = DOCUMENT_SIGNED;
    private final UpdateApplicationStatusHistory updateApplicationStatusHistory;

    @Autowired
    public CheckSendCodeProducerService(ApplicationRepository applicationRepository, CreditRepository creditRepository, KafkaProducer kafkaProducer,
                                        @Value("${spring.kafka.producer.credit-issued}") String topic, UpdateApplicationStatusHistory updateApplicationStatusHistory) {
        super(applicationRepository, creditRepository, kafkaProducer);
        this.topic = topic;
        this.updateApplicationStatusHistory = updateApplicationStatusHistory;
    }

    @Override
    protected void updateApplication(Application application, Long applicationId) {
        updateApplicationStatusHistory.updateApplicationStatusHistory(application, AFTER_STATUS, AUTOMATIC);
        LOGGER.info("Application for check code updated with ID: {}", applicationId);
        LOGGER.info("Status application is {}", AFTER_STATUS);
        Credit credit = application.getCredit();
        credit.setCredit_status(ISSUED);
        creditRepository.save(credit);
        LOGGER.info("Credit updated status: {}", ISSUED);
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
