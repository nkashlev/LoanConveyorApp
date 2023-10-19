package ru.nkashlev.api_gateway.gateway.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.api_gateway.model.CreditDTO;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;
import ru.nkashlev.api_gateway.model.ScoringDataDTO;

import java.util.List;

@FeignClient(name = "conveyorFeignClient", url = "http://localhost:8081/conveyor")
public interface ConveyorFeignClient {
    @PostMapping("/offers")
    ResponseEntity<List<LoanOfferDTO>> scoringLoanOffers(@RequestBody LoanApplicationRequestDTO request);

    @PostMapping("/calculation")
    ResponseEntity<CreditDTO> calculateLoanOffers(@RequestBody ScoringDataDTO request);
}
