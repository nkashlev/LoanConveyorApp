package ru.nkashlev.loan_application_app.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_application_app.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_application_app.model.LoanOfferDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private ConveyorApplicationClient conveyorApplicationClient;

    @InjectMocks
    private OfferService offerService;

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
    void createApplication_ValidRequest_ReturnsLoanOffers() {
        List<LoanOfferDTO> expectedOffers = Arrays.asList(new LoanOfferDTO(), new LoanOfferDTO());
        when(conveyorApplicationClient.loanApplication(any())).thenReturn(expectedOffers);

        List<LoanOfferDTO> actualOffers = offerService.createApplication(request);

        Assertions.assertEquals(expectedOffers, actualOffers);
        verify(conveyorApplicationClient).loanApplication(request);
    }

}