package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.CreateDocForSendApi;
import ru.nkashlev.loan_deal_app.deal.service.CreateDocumentsService;

@RestController
@RequiredArgsConstructor
public class LoanCreateDocuments implements CreateDocForSendApi {

    private final CreateDocumentsService createDocumentsService;
    @SneakyThrows
    @Override
    public ResponseEntity<Void> createDocuments(@PathVariable("applicationId") Long applicationId) {
        createDocumentsService.sendMessageToKafka(applicationId);
        return ResponseEntity.ok().build();
    }
}
