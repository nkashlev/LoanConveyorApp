package ru.nkashlev.loan_application_app.application.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;



@FeignClient(name = "dealOffer", url = "http://localhost:8080/deal")
public interface ConveyorOfferClient {
    @PutMapping("/offer")
    ResponseEntity<Void> loanOffer(@RequestBody LoanOfferDTO request);
}
