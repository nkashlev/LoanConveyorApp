package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.SendSesCodeApi;
import ru.nkashlev.loan_deal_app.deal.model.SendSesCodeRequest;
import ru.nkashlev.loan_deal_app.deal.service.CheckSendCodeProducerService;

@RequiredArgsConstructor
@RestController
public class LoanCheckSentCodeController implements SendSesCodeApi {

    private final CheckSendCodeProducerService checkSendCodeProducerService;

    @SneakyThrows
    @Override
    public ResponseEntity<Void> sendSesCode(@PathVariable Long applicationId, @RequestBody SendSesCodeRequest sendSesCodeRequest) {
        checkSendCodeProducerService.sendMessageToKafka(applicationId, sendSesCodeRequest);
        return ResponseEntity.ok().build();
    }
}
