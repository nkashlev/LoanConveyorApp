package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nkashlev.api_gateway.gateway.service.feign.ConveyorFeignClient;
import ru.nkashlev.api_gateway.model.CreditDTO;
import ru.nkashlev.api_gateway.model.ScoringDataDTO;

@RequiredArgsConstructor
@Service
public class LoanConveyorCalculateService {

    private final ConveyorFeignClient conveyorFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanConveyorCalculateService.class);

    public ResponseEntity<CreditDTO> calculateLoanOffers(@RequestBody ScoringDataDTO scoringDataDTO) {
        LOGGER.info("Calculating loan offers: {}", scoringDataDTO);
        ResponseEntity<CreditDTO> response = conveyorFeignClient.calculateLoanOffers(scoringDataDTO);
        LOGGER.info("Calculate loan offers response: {}", response);
        return response;
    }
}
