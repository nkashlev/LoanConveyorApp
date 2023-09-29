package ru.nkashlev.loan_deal_app.deal.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.service.SingDocumentsProducerService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LoanSingDocumentsControllerTest {
    @Mock
    private SingDocumentsProducerService singDocumentsProducerService;
    @InjectMocks
    private LoanSingDocumentsController loanSingDocumentsController;

    @Test
    void singDocuments() throws ResourceNotFoundException {
        Long applicationId = 1L;
        ResponseEntity<Void> response = loanSingDocumentsController.singDocuments(applicationId);
        assert  response.getStatusCode() == HttpStatus.OK;
        verify(singDocumentsProducerService).sendMessageToKafka(applicationId);
        verify(singDocumentsProducerService, times(1)).sendMessageToKafka(anyLong());
    }
}