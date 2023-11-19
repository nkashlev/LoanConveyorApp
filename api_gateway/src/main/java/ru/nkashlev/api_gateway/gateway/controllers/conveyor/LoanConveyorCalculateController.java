package ru.nkashlev.api_gateway.gateway.controllers.conveyor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.LoanCalculationApi;
import ru.nkashlev.api_gateway.gateway.service.LoanConveyorCalculateService;
import ru.nkashlev.api_gateway.model.CreditDTO;
import ru.nkashlev.api_gateway.model.ScoringDataDTO;
@RequiredArgsConstructor
@RestController
public class LoanConveyorCalculateController implements LoanCalculationApi {

    private final LoanConveyorCalculateService conveyorCalculateService;
    @Override
    public ResponseEntity<CreditDTO> calculateLoanOffers(@RequestBody ScoringDataDTO scoringDataDTO) {
        return conveyorCalculateService.calculateLoanOffers(scoringDataDTO);
    }
}
