package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;

@RequiredArgsConstructor
@Service
public class LoanDealSignDocumentsService {
    private final DealFeignClient dealFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDealSignDocumentsService.class);

    public ResponseEntity<Void> signDocuments(Long applicationId) {
        LOGGER.info("Signing documents for loan application with ID: {}", applicationId);

        ResponseEntity<Void> responseEntity = dealFeignClient.signDocuments(applicationId);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("Documents signed successfully for loan application with ID: {}", applicationId);
        } else {
            LOGGER.error("Failed to sign documents for loan application with ID: {}", applicationId);
        }

        return responseEntity;
    }
}
