package ru.nkashlev.loan_application_app.application.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_application_app.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);
    private  final ConveyorApplicationClient conveyorApplicationClient;

    public List<LoanOfferDTO> createApplication(LoanApplicationRequestDTO request) {
        List<LoanOfferDTO> offers = conveyorApplicationClient.loanApplication(request);
        LOGGER.info("New loan application created for client with email: {}", request.getEmail());
        return offers;
    }
}
