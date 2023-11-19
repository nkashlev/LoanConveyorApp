package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.CreateDocumentsApi;
import ru.nkashlev.api_gateway.gateway.service.DealFeignClient;

@RequiredArgsConstructor
@RestController
public class LoanDealCreateDocumentsController implements CreateDocumentsApi {

    private final DealFeignClient dealFeignClient;
    @Override
    public ResponseEntity<Void> createDocuments(Long applicationId) {
        return dealFeignClient.createDocuments(applicationId);
    }
}
