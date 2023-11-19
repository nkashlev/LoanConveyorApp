package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LoanDealGetAllApplicationsService {

    private final DealFeignClient dealFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDealGetAllApplicationsService.class);

    public ResponseEntity<List<LoanApplicationRequestDTO>> getAll() {
        LOGGER.info("Retrieving all loan applications");

        ResponseEntity<List<LoanApplicationRequestDTO>> responseEntity = dealFeignClient.getAll();

        LOGGER.info("Retrieved {} loan applications", Objects.requireNonNull(responseEntity.getBody()).size());

        return responseEntity;
    }
}
