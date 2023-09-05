package ru.nkashlev.loan_deal_app.deal.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.service.LoanService;

@SpringBootTest
public class LoanApplicationControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanApplicationController loanApplicationController;

    @Test
    public void testLoanApplication() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO();
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        loanOffers.add(new LoanOfferDTO());
        when(loanService.createApplication(any(LoanApplicationRequestDTO.class))).thenReturn(loanOffers);

        ResponseEntity<List<LoanOfferDTO>> response = loanApplicationController.loanApplication(request);

        assert response.getStatusCode() == HttpStatus.OK;
        assert Objects.requireNonNull(response.getBody()).size() == 1;
    }
}