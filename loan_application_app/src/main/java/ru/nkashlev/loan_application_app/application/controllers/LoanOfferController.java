package ru.nkashlev.loan_application_app.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_application_app.application.api.CreateApplicationApi;
import ru.nkashlev.loan_application_app.application.exceptions.ScoringException;
import ru.nkashlev.loan_application_app.application.service.OfferService;
import ru.nkashlev.loan_application_app.application.util.ValidateLoanApplicationRequestUtil;
import ru.nkashlev.loan_application_app.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class LoanOfferController implements CreateApplicationApi {

    private final OfferService offerService;

    private final ValidateLoanApplicationRequestUtil validateRequestService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> loanApplication(@RequestBody LoanApplicationRequestDTO request) {
        List<String> validateRequest = validateRequestService.validateRequest(request);
        if (!validateRequest.isEmpty()) {
            throw new ScoringException(validateRequest);
        }
        return ResponseEntity.ok(offerService.createApplication(request));
    }
}
