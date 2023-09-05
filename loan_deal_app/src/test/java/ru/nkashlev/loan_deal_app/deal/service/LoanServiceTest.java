package ru.nkashlev.loan_deal_app.deal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ConveyorOfferClient conveyorOfferClient;


    @InjectMocks
    private LoanService loanService;

    private LoanApplicationRequestDTO request;

    @BeforeEach
    public void setUp() {
        request = new LoanApplicationRequestDTO();
        request.setFirstName("John");
        request.setMiddleName("Doe");
        request.setLastName("Smith");
        request.setBirthdate(LocalDate.of(1990, 1, 1));
        request.setEmail("john.doe@example.com");
        request.setPassportNumber("123456");
        request.setPassportSeries("1234");
    }

    @Test
    public void testCreateApplication() {
        List<LoanOfferDTO> offers = new ArrayList<>();
        LoanOfferDTO offer1 = new LoanOfferDTO();
        offer1.setTotalAmount(new BigDecimal("10000"));
        offer1.setRate(new BigDecimal("0.05"));
        offers.add(offer1);
        LoanOfferDTO offer2 = new LoanOfferDTO();
        offer2.setTotalAmount(new BigDecimal("20000"));
        offer2.setRate(new BigDecimal("0.06"));
        offers.add(offer2);

        when(conveyorOfferClient.calculateLoanOffers(any(LoanApplicationRequestDTO.class))).thenReturn(offers);

        List<LoanOfferDTO> result = loanService.createApplication(request);

        assertEquals(offers, result);
    }

}