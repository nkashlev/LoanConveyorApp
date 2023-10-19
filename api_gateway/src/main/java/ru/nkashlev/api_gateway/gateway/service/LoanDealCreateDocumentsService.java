package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;

@RequiredArgsConstructor
@Service
public class LoanDealCreateDocumentsService {

    private final DealFeignClient dealFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDealCreateDocumentsService.class);

    public ResponseEntity<Void> createDocuments(Long applicationId) {
        LOGGER.info("Creating documents for applicationId: {}", applicationId);

        ResponseEntity<Void> responseEntity = dealFeignClient.createDocuments(applicationId);

        LOGGER.info("Document creation response: {}", responseEntity);

        return responseEntity;
    }
}
