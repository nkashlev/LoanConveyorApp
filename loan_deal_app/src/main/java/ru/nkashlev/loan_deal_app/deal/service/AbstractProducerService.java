package ru.nkashlev.loan_deal_app.deal.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.util.EmailMessage;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.SendSesCodeRequest;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.CreditRepository;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;

@Data
@RequiredArgsConstructor
@Service
public abstract class AbstractProducerService {

    protected final ApplicationRepository applicationRepository;

    protected final CreditRepository creditRepository;

    private final KafkaProducer kafkaProducer;

    private final Logger LOGGER = LoggerFactory.getLogger(AbstractProducerService.class);

    protected final ApplicationUtil applicationUtil;

    public void sendMessageToKafka(Long applicationId) throws ResourceNotFoundException {
        Application application = checkAndUpdateApplication(applicationId);
        String topic = getTopic();
        StatusEnum AFTER_STATUS = getAfterStatus();
        EmailMessage message = new EmailMessage(application.getClient().getEmail(), application.getApplicationId(), AFTER_STATUS);
        kafkaProducer.sendMessage(topic, message);
        LOGGER.info("Message sent to kafka with topic - {}", topic);
    }

    public void sendMessageToKafka(Long applicationId, SendSesCodeRequest request) throws ResourceNotFoundException {
        Application application = checkAndUpdateApplication(applicationId, request);
        String topic = getTopic();
        StatusEnum AFTER_STATUS = getAfterStatus();
        EmailMessage message = new EmailMessage(application.getClient().getEmail(), application.getApplicationId(), AFTER_STATUS);
        kafkaProducer.sendMessage(topic, message);
        LOGGER.info("Message sent to kafka with topic - {}", topic);
    }

    protected Application checkAndUpdateApplication(Long applicationId) throws ResourceNotFoundException {
        Application application = applicationUtil.findApplicationById(applicationId);
        StatusEnum BEFORE_STATUS = getBeforeStatus();
        if (application.getStatus().equals(BEFORE_STATUS)) {
            updateApplication(application, applicationId);
            LOGGER.info("Application for document creation updated with ID: {}", applicationId);
        } else {
            LOGGER.info("Application does not exist or the status is incorrect");
            throw new ResourceNotFoundException("Application does not exist or the status is incorrect");
        }
        return application;
    }

    protected Application checkAndUpdateApplication(Long applicationId, SendSesCodeRequest request) throws ResourceNotFoundException {
        Application application = applicationUtil.findApplicationById(applicationId);
        StatusEnum BEFORE_STATUS = getBeforeStatus();
        if (application.getStatus().equals(BEFORE_STATUS) && application.getSesCode().equals(request.getCode())) {
           updateApplication(application, applicationId);
        } else {
            LOGGER.info("The application does not exist, or its status is incorrect, or the code does not match");
            throw new ResourceNotFoundException("The application does not exist, or its status is incorrect, or the code does not match");
        }
        return application;
    }

    protected abstract void updateApplication(Application application, Long applicationId);

    protected abstract String getTopic();

    protected abstract StatusEnum getBeforeStatus();

    protected abstract StatusEnum getAfterStatus();
}
