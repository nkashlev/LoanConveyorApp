package ru.nkashlev.loan_deal_app.deal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;

import java.util.List;

@FeignClient(name = "conveyorOffers", url = "http://localhost:8081/conveyor")
public interface ConveyorOfferClient {
    @PostMapping("/offers")
    List<LoanOfferDTO> calculateLoanOffers(@RequestBody LoanApplicationRequestDTO request);
}
