package ru.nkashlev.loan_deal_app.deal.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.FinishRegistrationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.service.CalculateService;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class LoanCalculateControllerTest {

    @Mock
    private CalculateService calculateService;

    @InjectMocks
    private LoanCalculateController loanCalculateController;

    @Test
    public void testFinishRegistration() throws ResourceNotFoundException {
        Long applicationId = 1L;
        FinishRegistrationRequestDTO request = new FinishRegistrationRequestDTO();

        ResponseEntity<Void> response = loanCalculateController.dealCalculateApplicationIdPut(applicationId, request);

        assert response.getStatusCode() == HttpStatus.OK;
        verify(calculateService).finishRegistration(applicationId, request);
    }
}