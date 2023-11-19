package ru.nkashlev.loan_deal_app.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.nkashlev.loan_deal_app.deal.api.GetApplicationByIdApi;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

@RestController
@RequiredArgsConstructor
public class LoanGetApplicationController implements GetApplicationByIdApi {

    private final ApplicationUtil applicationUtil;

    @SneakyThrows
    @Override
    public ResponseEntity<LoanApplicationRequestDTO> get(Long applicationId) {
        return ResponseEntity.ok(applicationUtil.getApplication(applicationId));
    }
}
