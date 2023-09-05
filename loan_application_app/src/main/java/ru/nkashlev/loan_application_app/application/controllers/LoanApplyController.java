package ru.nkashlev.loan_application_app.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_application_app.application.api.ApplyOfferApi;
import ru.nkashlev.loan_application_app.application.service.ApplyOfferService;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

@RestController
@RequiredArgsConstructor
public class LoanApplyController implements ApplyOfferApi {
    private final ApplyOfferService applyOfferService;

    @Override
    public ResponseEntity<Void> loanOfferApply(@RequestBody LoanOfferDTO request) {
        applyOfferService.applyOffer(request);
        return ResponseEntity.ok().build();
    }
}
