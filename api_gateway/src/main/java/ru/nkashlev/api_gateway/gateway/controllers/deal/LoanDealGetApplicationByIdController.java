package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.GetApplicationByIdApi;
import ru.nkashlev.api_gateway.gateway.service.LoanDealGetApplicationByIdService;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;

@RestController
@RequiredArgsConstructor
public class LoanDealGetApplicationByIdController implements GetApplicationByIdApi {

    private final LoanDealGetApplicationByIdService dealGetApplicationByIdService;
    @Override
    public ResponseEntity<LoanApplicationRequestDTO> get(@PathVariable Long applicationId) {
        return dealGetApplicationByIdService.get(applicationId);
    }
}
