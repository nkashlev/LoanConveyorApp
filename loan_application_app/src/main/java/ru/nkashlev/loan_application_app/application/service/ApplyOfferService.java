package ru.nkashlev.loan_application_app.application.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

@Service
@RequiredArgsConstructor
public class ApplyOfferService {

    private final ConveyorOfferClient client;
    private final Logger LOGGER = LoggerFactory.getLogger(ApplyOfferService.class);

    public void applyOffer(LoanOfferDTO request) {
        client.loanOffer(request);
        LOGGER.info("Application updated with ID: {}", request.getApplicationId());
    }
}
