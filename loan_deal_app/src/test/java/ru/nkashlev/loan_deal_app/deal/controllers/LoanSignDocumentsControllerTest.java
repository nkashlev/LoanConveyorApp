package ru.nkashlev.loan_deal_app.deal.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.service.SignDocumentsProducerService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LoanSignDocumentsControllerTest {
    @Mock
    private SignDocumentsProducerService signDocumentsProducerService;
    @InjectMocks
    private LoanSignDocumentsController loanSignDocumentsController;

    @Test
    void singDocuments() throws ResourceNotFoundException {
        Long applicationId = 1L;
        ResponseEntity<Void> response = loanSignDocumentsController.signDocuments(applicationId);
        assert  response.getStatusCode() == HttpStatus.OK;
        verify(signDocumentsProducerService).sendMessageToKafka(applicationId);
        verify(signDocumentsProducerService, times(1)).sendMessageToKafka(anyLong());
    }
}