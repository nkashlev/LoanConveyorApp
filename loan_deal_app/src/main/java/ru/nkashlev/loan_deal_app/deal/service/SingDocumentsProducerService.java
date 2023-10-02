package ru.nkashlev.loan_deal_app.deal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.CreditRepository;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import java.util.Random;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.DOCUMENT_CREATED;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.PREPARE_DOCUMENTS;


@Service
public class SingDocumentsProducerService extends AbstractProducerService {
    private final String topic;
    private final Logger LOGGER = LoggerFactory.getLogger(SingDocumentsProducerService.class);
    private final static StatusEnum BEFORE_STATUS = PREPARE_DOCUMENTS;
    private final static StatusEnum AFTER_STATUS = DOCUMENT_CREATED;
    private final Random random = new Random();

    @Autowired
    public SingDocumentsProducerService(ApplicationRepository applicationRepository, CreditRepository creditRepository, KafkaProducer kafkaProducer,
                                        @Value("${spring.kafka.producer.send-ses}") String topic, ApplicationUtil applicationUtil) {
        super(applicationRepository, creditRepository, kafkaProducer, applicationUtil);
        this.topic = topic;
    }

    @Override
    protected void updateApplication(Application application, Long applicationId) {
        applicationUtil.updateApplicationStatusHistory(application, AFTER_STATUS, AUTOMATIC);
        application.setSesCode(generateCode());
        applicationRepository.save(application);
        LOGGER.info("Application for send code updated with ID: {}", applicationId);
        LOGGER.info("Status application is {}  ", AFTER_STATUS);
    }

    private Long generateCode() {
        Long code = random.nextLong(9000L) + 1000L;
        LOGGER.info("Generated code: {}", code);
        return code;
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
