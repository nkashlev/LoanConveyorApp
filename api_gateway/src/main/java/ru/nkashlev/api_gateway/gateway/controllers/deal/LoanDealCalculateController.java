package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.CalculateDocumentsApi;
import ru.nkashlev.api_gateway.gateway.service.DealFeignClient;
import ru.nkashlev.api_gateway.model.FinishRegistrationRequestDTO;

@RequiredArgsConstructor
@RestController
public class LoanDealCalculateController implements CalculateDocumentsApi {

    private final DealFeignClient dealFeignClient;
    @Override
    public ResponseEntity<Void> dealCalculateApplicationIdPut(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        return dealFeignClient.dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO);
    }
}
