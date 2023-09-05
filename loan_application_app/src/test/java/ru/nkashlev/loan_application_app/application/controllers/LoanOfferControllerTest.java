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
import ru.nkashlev.loan_application_app.application.exceptions.ScoringException;
import ru.nkashlev.loan_application_app.application.service.OfferService;
import ru.nkashlev.loan_application_app.application.util.ValidateLoanApplicationRequestUtil;
import ru.nkashlev.loan_application_app.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanOfferControllerTest {

    @Mock
    private OfferService offerService;

    @Mock
    private ValidateLoanApplicationRequestUtil validateRequestService;

    @InjectMocks
    private LoanOfferController loanOfferController;

    private LoanApplicationRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new LoanApplicationRequestDTO();
        request.amount(new BigDecimal("10000"));
        request.term(12L);
        request.firstName("Joe");
        request.middleName("Joi");
        request.lastName("Doy");
        request.email("doy@mail.com");
        request.birthdate(LocalDate.of(1997, Month.JANUARY, 21));
    }

    @Test
    void preScoringLoanOffers_ValidRequest_ReturnsLoanOffers() {
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        loanOffers.add(new LoanOfferDTO());

        when(validateRequestService.validateRequest(any())).thenReturn(new ArrayList<>());
        when(offerService.createApplication(any())).thenReturn(loanOffers);

        ResponseEntity<List<LoanOfferDTO>> response = loanOfferController.preScoringLoanOffers(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(loanOffers, response.getBody());
    }

    @Test
    void preScoringLoanOffers_InvalidRequest_ThrowsScoringException() {
        List<String> errors = new ArrayList<>();
        errors.add("Invalid personal ID");

        when(validateRequestService.validateRequest(any())).thenReturn(errors);

        Assertions.assertThrows(ScoringException.class, () -> {
            loanOfferController.preScoringLoanOffers(request);
        });
    }
}