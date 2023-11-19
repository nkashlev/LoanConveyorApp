package ru.nkashlev.api_gateway.gateway.controllers.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.CreateApplicationApi;
import ru.nkashlev.api_gateway.gateway.service.LoanCreateApplicationService;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class LoanCreateApplicationController implements CreateApplicationApi {

    private final LoanCreateApplicationService createApplicationService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> loanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return createApplicationService.createApplications(loanApplicationRequestDTO);
    }
}
