package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;

@RequiredArgsConstructor
@Service
public class LoanDealGetApplicationByIdService {
    private final DealFeignClient dealFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDealGetApplicationByIdService.class);

    public ResponseEntity<LoanApplicationRequestDTO> get(@PathVariable Long applicationId) {
        LOGGER.info("Retrieving loan application with ID: {}", applicationId);

        ResponseEntity<LoanApplicationRequestDTO> responseEntity = dealFeignClient.get(applicationId);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("Retrieved loan application: {}", responseEntity.getBody());
        } else {
            LOGGER.error("Failed to retrieve loan application with ID: {}", applicationId);
        }

        return responseEntity;
    }
}
