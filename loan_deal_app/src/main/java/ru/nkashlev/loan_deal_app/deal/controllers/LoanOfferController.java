package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.OfferApi;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.service.OfferService;

@RestController
@RequiredArgsConstructor
public class LoanOfferController implements OfferApi {
    private final OfferService offerService;

    @SneakyThrows
    @Override
    public ResponseEntity<Void> loanOffer(@RequestBody LoanOfferDTO request) {
        offerService.updateApplication(request);
        return ResponseEntity.ok().build();
    }
}
