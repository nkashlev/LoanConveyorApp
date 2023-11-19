package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.SingDocumentsApi;
import ru.nkashlev.api_gateway.gateway.service.DealFeignClient;

@RequiredArgsConstructor
@RestController
public class LoanDealSingDocumentsController implements SingDocumentsApi {

    private final DealFeignClient dealFeignClient;
    @Override
    public ResponseEntity<Void> singDocuments(Long applicationId) {
        return dealFeignClient.singDocuments(applicationId);
    }
}
