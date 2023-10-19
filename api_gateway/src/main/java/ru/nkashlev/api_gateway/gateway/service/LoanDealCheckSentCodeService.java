package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;
import ru.nkashlev.api_gateway.model.SendSesCodeRequest;

@RequiredArgsConstructor
@Service
public class LoanDealCheckSentCodeService {

    private final DealFeignClient dealFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDealCheckSentCodeService.class);

    public ResponseEntity<Void> sendSesCode(Long applicationId, SendSesCodeRequest sendSesCodeRequest) {
        LOGGER.info("Sending SES code for applicationId: {}", applicationId);

        ResponseEntity<Void> responseEntity = dealFeignClient.sendSesCode(applicationId, sendSesCodeRequest);

        LOGGER.info("SES code sending response: {}", responseEntity);

        return responseEntity;
    }
}
