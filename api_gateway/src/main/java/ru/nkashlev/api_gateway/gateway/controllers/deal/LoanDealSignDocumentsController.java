package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.SignDocumentsApi;
import ru.nkashlev.api_gateway.gateway.service.LoanDealSignDocumentsService;

@RequiredArgsConstructor
@RestController
public class LoanDealSignDocumentsController implements SignDocumentsApi {

    private final LoanDealSignDocumentsService dealSignDocumentsService;
    @Override
    public ResponseEntity<Void> signDocuments(Long applicationId) {
        return dealSignDocumentsService.signDocuments(applicationId);
    }
}
