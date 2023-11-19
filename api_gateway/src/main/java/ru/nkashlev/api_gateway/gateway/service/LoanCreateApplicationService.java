package ru.nkashlev.api_gateway.gateway.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nkashlev.api_gateway.gateway.service.feign.ApplicationFeignClient;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoanCreateApplicationService {

    private final ApplicationFeignClient applicationFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanCreateApplicationService.class);

    public ResponseEntity<List<LoanOfferDTO>> createApplications(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LOGGER.info("Creating loan applications: {}", loanApplicationRequestDTO);
        ResponseEntity<List<LoanOfferDTO>> response = applicationFeignClient.loanApplication(loanApplicationRequestDTO);
        LOGGER.info("Create applications response: {}", response);
        return response;
    }
}
