package ru.nkashlev.loan_deal_app.deal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.loan_deal_app.deal.model.CreditDTO;
import ru.nkashlev.loan_deal_app.deal.model.ScoringDataDTO;

@FeignClient(name = "LoanCalculation", url = "http://localhost:8081/conveyor")
public interface ConveyorCalculationClient {
    @PostMapping("/calculation")
    CreditDTO calculateLoanOffers(@RequestBody ScoringDataDTO request);
}

