package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ru.nkashlev.loan_deal_app.deal.api.SignDocumentsApi;
import ru.nkashlev.loan_deal_app.deal.service.SignDocumentsProducerService;

@RequiredArgsConstructor
@RestController
public class LoanSignDocumentsController implements SignDocumentsApi {

    private final SignDocumentsProducerService signDocumentsProducerService;
    @SneakyThrows
    @Override
    public ResponseEntity<Void> signDocuments(@PathVariable Long applicationId) {
        signDocumentsProducerService.sendMessageToKafka(applicationId);
        return ResponseEntity.ok().build();
    }
}
