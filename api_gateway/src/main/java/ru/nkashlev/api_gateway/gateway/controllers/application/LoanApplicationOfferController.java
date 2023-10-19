package ru.nkashlev.api_gateway.gateway.controllers.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.ApplyOfferApi;
import ru.nkashlev.api_gateway.gateway.service.LoanApplicationOfferService;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;
@RestController
@RequiredArgsConstructor
public class LoanApplicationOfferController implements ApplyOfferApi {

    private final LoanApplicationOfferService offerService;
    @Override
    public ResponseEntity<Void> loanOffer(LoanOfferDTO loanOfferDTO) {
        return offerService.applyOffer(loanOfferDTO);
    }
}
