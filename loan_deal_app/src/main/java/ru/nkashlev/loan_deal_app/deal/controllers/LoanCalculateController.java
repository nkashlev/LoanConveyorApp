package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.DefaultApi;
import ru.nkashlev.loan_deal_app.deal.model.FinishRegistrationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.service.CalculateService;

@RestController
@RequiredArgsConstructor
public class LoanCalculateController implements DefaultApi {

    private final CalculateService calculateService;


    @SneakyThrows
    @Override
    public ResponseEntity<Void> dealCalculateApplicationIdPut(@PathVariable("application_id") Long applicationId, @RequestBody FinishRegistrationRequestDTO request) {
        calculateService.finishRegistration(applicationId, request);
        return ResponseEntity.ok().build();

    }
}
