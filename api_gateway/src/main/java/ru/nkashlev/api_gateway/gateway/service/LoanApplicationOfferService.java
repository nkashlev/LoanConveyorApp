package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.ApplicationFeignClient;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

@RequiredArgsConstructor
@Service
public class LoanApplicationOfferService {

    private final ApplicationFeignClient applicationFeignClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplicationOfferService.class);
    public ResponseEntity<Void> applyOffer(LoanOfferDTO loanOfferDTO) {
        LOGGER.info("Applying offer: {}", loanOfferDTO);
        ResponseEntity<Void> response = applicationFeignClient.loanOffer(loanOfferDTO);
        LOGGER.info("Apply offer response: {}", response);
        return response;
    }
}
