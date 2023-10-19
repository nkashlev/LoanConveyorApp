package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.SendSesCodeApi;
import ru.nkashlev.api_gateway.gateway.service.LoanDealCheckSentCodeService;
import ru.nkashlev.api_gateway.model.SendSesCodeRequest;

@RequiredArgsConstructor
@RestController
public class LoanDealCheckSentCodeController implements SendSesCodeApi {

    private final LoanDealCheckSentCodeService dealCheckSentCodeService;
    @Override
    public ResponseEntity<Void> sendSesCode(Long applicationId, SendSesCodeRequest sendSesCodeRequest) {
        return dealCheckSentCodeService.sendSesCode(applicationId, sendSesCodeRequest);
    }
}
