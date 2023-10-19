package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;
import ru.nkashlev.api_gateway.model.FinishRegistrationRequestDTO;

@RequiredArgsConstructor
@Service
public class LoanDealCalculateService {

    private final DealFeignClient dealFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDealCalculateService.class);

    public ResponseEntity<Void> dealCalculateApplicationIdPut(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        LOGGER.info("Calculating deal for applicationId: {}", applicationId);

        ResponseEntity<Void> responseEntity = dealFeignClient.dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO);

        LOGGER.info("Deal calculation response: {}", responseEntity);

        return responseEntity;
    }
}
