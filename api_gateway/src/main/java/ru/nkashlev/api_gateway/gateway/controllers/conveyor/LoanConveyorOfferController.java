package ru.nkashlev.api_gateway.gateway.controllers.conveyor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.LoanOfferApi;
import ru.nkashlev.api_gateway.gateway.service.LoanConveyorOfferService;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoanConveyorOfferController implements LoanOfferApi {

    private final LoanConveyorOfferService conveyorOfferService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> scoringLoanOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return conveyorOfferService.scoringLoanOffers(loanApplicationRequestDTO);
    }
}
