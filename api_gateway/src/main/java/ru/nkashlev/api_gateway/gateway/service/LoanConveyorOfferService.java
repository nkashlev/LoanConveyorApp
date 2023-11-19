package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.ConveyorFeignClient;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoanConveyorOfferService {

    private final ConveyorFeignClient conveyorFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanConveyorOfferService.class);

    public ResponseEntity<List<LoanOfferDTO>> scoringLoanOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LOGGER.info("Scoring loan offers: {}", loanApplicationRequestDTO);
        ResponseEntity<List<LoanOfferDTO>> responseEntity = conveyorFeignClient.scoringLoanOffers(loanApplicationRequestDTO);
        LOGGER.info("Scoring loan offers response: {}", responseEntity);
        return responseEntity;
    }
}
