package ru.nkashlev.loan_application_app.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApplyOfferServiceTest {
    @Mock
    private ConveyorOfferClient client;

    @InjectMocks
    private ApplyOfferService applyOfferService;

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
    void applyOffer_ValidRequest_UpdatesApplicationAndLogs() {
        applyOfferService.applyOffer(request);

        verify(client).loanOffer(request);
    }
}