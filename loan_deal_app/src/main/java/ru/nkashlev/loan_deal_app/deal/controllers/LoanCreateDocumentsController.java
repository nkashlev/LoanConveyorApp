package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.CreateDocumentsApi;
import ru.nkashlev.loan_deal_app.deal.service.CreateDocumentsProducerService;

@RestController
@RequiredArgsConstructor
public class LoanCreateDocumentsController implements CreateDocumentsApi {

    private final CreateDocumentsProducerService createDocumentsProducerService;
    @SneakyThrows
    @Override
    public ResponseEntity<Void> createDocuments(@PathVariable("applicationId") Long applicationId) {
        createDocumentsProducerService.sendMessageToKafka(applicationId);
        return ResponseEntity.ok().build();
    }
}
