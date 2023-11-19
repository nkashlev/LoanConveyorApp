package ru.nkashlev.api_gateway.gateway.controllers.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.api_gateway.gateway.api.GetAllApplicationsApi;
import ru.nkashlev.api_gateway.gateway.service.LoanDealGetAllApplicationsService;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanDealGetAllApplicationsController implements GetAllApplicationsApi {

    private final LoanDealGetAllApplicationsService dealGetAllApplicationsService;
    @Override
    public ResponseEntity<List<LoanApplicationRequestDTO>> getAll() {
        return dealGetAllApplicationsService.getAll();
    }
}
