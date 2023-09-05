package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.ApplicationApi;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.service.LoanService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanApplicationController implements ApplicationApi {

    private final LoanService loanService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> loanApplication(@RequestBody LoanApplicationRequestDTO request) {
        return ResponseEntity.ok(loanService.createApplication(request));
    }
}
