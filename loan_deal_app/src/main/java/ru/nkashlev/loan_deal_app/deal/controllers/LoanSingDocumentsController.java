package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.SingDocumentsApi;
import ru.nkashlev.loan_deal_app.deal.service.SingDocumentsProducerService;

@RequiredArgsConstructor
@RestController
public class LoanSingDocumentsController implements SingDocumentsApi {

    private final SingDocumentsProducerService singDocumentsProducerService;
    @SneakyThrows
    @Override
    public ResponseEntity<Void> singDocuments(@PathVariable Long applicationId) {
        singDocumentsProducerService.sendMessageToKafka(applicationId);
        return ResponseEntity.ok().build();
    }
}
