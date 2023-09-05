package ru.nkashlev.loan_deal_app.deal.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;

@Component
@RequiredArgsConstructor
public class FindIdByApplication {
    private final ApplicationRepository applicationRepository;
    Logger LOGGER = LoggerFactory.getLogger(FindIdByApplication.class);

    public Application findIdByApplication(Long id) throws ResourceNotFoundException {
        LOGGER.info("Started to find application with id: {}", id);
        Application application = applicationRepository.findById(id).orElse(null);
        if (application == null) {
            throw new ResourceNotFoundException("Cannot find application with id: " + id);
        }
        LOGGER.info("Found application with id: {}", id);
        return application;
    }
}
