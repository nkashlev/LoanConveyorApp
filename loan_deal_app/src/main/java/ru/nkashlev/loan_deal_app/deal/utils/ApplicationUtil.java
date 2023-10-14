package ru.nkashlev.loan_deal_app.deal.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.Credit;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;

@RequiredArgsConstructor
@Component
public class ApplicationUtil {

    private final ApplicationRepository applicationRepository;
    Logger LOGGER = LoggerFactory.getLogger(ApplicationUtil.class);

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

    public Application findApplicationById(Long id) throws ResourceNotFoundException {
        LOGGER.info("Started to find application with id: {}", id);
        Application application = applicationRepository.findById(id).orElse(null);
        if (application == null) {
            LOGGER.info("Cannot find application with id: {}", id);
            throw new ResourceNotFoundException("Cannot find application with id: " + id);
        }
        LOGGER.info("Found application with id: {}", id);
        return application;
    }

    public LoanApplicationRequestDTO getApplication(Long id) throws ResourceNotFoundException {
        Application application = applicationRepository.findByIdNotNull(id);
        if (application == null) {
            LOGGER.info("Cannot find application with id: {}", id);
            throw new ResourceNotFoundException("Cannot find application with id: " + id);
        }
        LOGGER.info("Application returned with id: {}", id);
        return new LoanApplicationRequestDTO().firstName(application.getClient().getFirst_name())
                .middleName(application.getClient().getMiddle_name())
                .lastName(application.getClient().getLast_name())
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .email(application.getClient().getEmail())
                .birthdate(application.getClient().getBirth_date())
                .passportSeries(application.getClient().getPassport().getSeries())
                .passportNumber(application.getClient().getPassport().getNumber());

    }

    public List<LoanApplicationRequestDTO> getAll() throws ResourceNotFoundException {
        Iterable<Application> iterable = applicationRepository.findAllNotNullElements();
        if (iterable == null) {
            LOGGER.info("Cannot find applications");
            throw new ResourceNotFoundException("Cannot find applications");
        }
        ArrayList<LoanApplicationRequestDTO> applications = new ArrayList<>();
        for (Application application : iterable) {
            applications.add(new LoanApplicationRequestDTO().firstName(application.getClient().getFirst_name())
                    .middleName(application.getClient().getMiddle_name())
                    .lastName(application.getClient().getLast_name())
                    .amount(application.getCredit().getAmount())
                    .term(application.getCredit().getTerm())
                    .email(application.getClient().getEmail())
                    .birthdate(application.getClient().getBirth_date())
                    .passportSeries(application.getClient().getPassport().getSeries())
                    .passportNumber(application.getClient().getPassport().getNumber()));
        }
        LOGGER.info("Applications returned: {}", applications);
        return applications;
    }
}
