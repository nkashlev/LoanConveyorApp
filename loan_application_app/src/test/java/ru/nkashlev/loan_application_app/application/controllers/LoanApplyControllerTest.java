package ru.nkashlev.loan_application_app.application.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_application_app.application.service.ApplyOfferService;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoanApplyControllerTest {

    @Mock
    private ApplyOfferService applyOfferService;

    @InjectMocks
    private LoanApplyController loanApplyController;

    private LoanOfferDTO request;

    @BeforeEach
    void setUp() {
        request = new LoanOfferDTO();
        request.setApplicationId(1L);
        request.setRequestedAmount(new BigDecimal("10000"));
        request.setTerm(12L);
        request.setIsInsuranceEnabled(true);
        request.setIsSalaryClient(false);
    }

    @Test
    void loanOfferApply_ValidRequest_ReturnsOkResponse() {
        ResponseEntity<Void> response = loanApplyController.loanOfferApply(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(applyOfferService).applyOffer(any());
    }
}