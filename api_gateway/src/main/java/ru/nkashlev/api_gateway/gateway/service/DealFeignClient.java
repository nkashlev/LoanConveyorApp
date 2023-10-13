package ru.nkashlev.api_gateway.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.api_gateway.model.FinishRegistrationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;
import ru.nkashlev.api_gateway.model.SendSesCodeRequest;

import java.util.List;

@FeignClient(name = "dealFeignClient", url = "http://localhost:8080/deal")
public interface DealFeignClient {
//    @PostMapping("/application")
//    ResponseEntity<List<LoanOfferDTO>> loanApplication(@RequestBody LoanApplicationRequestDTO request);

    @PutMapping("/calculate/{application_id}")
    ResponseEntity<Void> dealCalculateApplicationIdPut(@PathVariable("application_id") Long applicationId, @RequestBody FinishRegistrationRequestDTO request);

    @PostMapping("/document/{applicationId}/code")
    ResponseEntity<Void> sendSesCode(@PathVariable Long applicationId, @RequestBody SendSesCodeRequest sendSesCodeRequest);

    @PostMapping("/document/{applicationId}/send")
    ResponseEntity<Void> createDocuments(@PathVariable("applicationId") Long applicationId);

//    @PutMapping("/offer")
//    ResponseEntity<Void> loanOffer(@RequestBody LoanOfferDTO request);

    @PostMapping("/document/{applicationId}/sign")
    ResponseEntity<Void> singDocuments(@PathVariable Long applicationId);
}
