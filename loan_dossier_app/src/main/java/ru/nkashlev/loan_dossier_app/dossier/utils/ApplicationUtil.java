package ru.nkashlev.loan_dossier_app.dossier.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_dossier_app.dossier.entity.Application;
import ru.nkashlev.loan_dossier_app.dossier.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_dossier_app.dossier.repositories.ApplicationRepository;


@Component
@RequiredArgsConstructor
public class ApplicationUtil {
    private final ApplicationRepository applicationRepository;
    Logger LOGGER = LoggerFactory.getLogger(ApplicationUtil.class);

    public Application findApplicationById(Long id) throws ResourceNotFoundException {
        LOGGER.info("Started to find application with id: {}", id);
        Application application = applicationRepository.findById(id).orElse(null);
        if (application == null) {
            throw new ResourceNotFoundException("Cannot find application with id: " + id);
        }
        LOGGER.info("Found application with id: {}", id);
        return application;
    }
}
