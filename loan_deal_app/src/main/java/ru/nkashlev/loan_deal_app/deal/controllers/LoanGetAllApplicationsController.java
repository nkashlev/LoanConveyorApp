package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.GetAllApplicationsApi;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanGetAllApplicationsController implements GetAllApplicationsApi {

    private final ApplicationUtil applicationUtil;
    @SneakyThrows
    @Override
    public ResponseEntity<List<LoanApplicationRequestDTO>> getAll() {
        return  ResponseEntity.ok(applicationUtil.getAll());
    }
}
