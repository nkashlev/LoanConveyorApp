package ru.nkashlev.loan_application_app.application.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.loan_application_app.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.util.List;


@FeignClient(name = "dealApplication", url = "http://localhost:8080/deal")
public interface ConveyorApplicationClient {
    @PostMapping("/application")
    List<LoanOfferDTO> loanApplication(@RequestBody LoanApplicationRequestDTO request);
}
