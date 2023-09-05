package ru.nkashlev.loan_deal_app.deal.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.Credit;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;

import java.time.LocalDate;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;

@RequiredArgsConstructor
@Component
public class UpdateApplicationStatusHistory {

    private final ApplicationRepository applicationRepository;
    Logger LOGGER = LoggerFactory.getLogger(UpdateApplicationStatusHistory.class);

    public void updateApplicationStatusHistory(Application application, StatusEnum status, ChangeTypeEnum changeType) {
        LOGGER.info("Started to update application with id: {}", application.getApplicationId());
        application.setStatus(status);
        ApplicationStatusHistoryDTO statusHistory = new ApplicationStatusHistoryDTO();
        statusHistory.setStatus(status);
        statusHistory.setTime(LocalDate.now());
        statusHistory.setChangeType(changeType);
        application.getStatusHistory().add(statusHistory);
        applicationRepository.save(application);
    }

    public void updateApplicationStatusHistory(Application application, StatusEnum status, ChangeTypeEnum changeType, Credit credit) {
        LOGGER.info("Started to update application with id: {}", application.getApplicationId());
        application.setStatus(status);
        ApplicationStatusHistoryDTO statusHistory = new ApplicationStatusHistoryDTO();
        statusHistory.setStatus(status);
        statusHistory.setTime(LocalDate.now());
        statusHistory.setChangeType(changeType);
        application.getStatusHistory().add(statusHistory);
        application.setCredit(credit);
        applicationRepository.save(application);
    }
}
