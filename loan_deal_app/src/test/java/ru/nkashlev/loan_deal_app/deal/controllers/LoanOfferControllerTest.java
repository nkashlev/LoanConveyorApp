package ru.nkashlev.loan_deal_app.deal.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.service.OfferService;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class LoanOfferControllerTest {

    @Mock
    private OfferService offerService;

    @InjectMocks
    private LoanOfferController loanOfferController;

    @Test
    public void testLoanOffer() throws ResourceNotFoundException {

        LoanOfferDTO request = new LoanOfferDTO();

        ResponseEntity<Void> response = loanOfferController.loanOffer(request);

        assert response.getStatusCode() == HttpStatus.OK;
        verify(offerService).updateApplication(request);
    }
}