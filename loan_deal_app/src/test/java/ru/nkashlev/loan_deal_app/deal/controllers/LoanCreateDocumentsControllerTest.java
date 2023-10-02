package ru.nkashlev.loan_deal_app.deal.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.service.CreateDocumentsProducerService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LoanCreateDocumentsControllerTest {
    @Mock
    private CreateDocumentsProducerService createDocumentsProducerService;
    @InjectMocks
    private LoanCreateDocumentsController loanCreateDocumentsController;

    @Test
    void createDocuments() throws ResourceNotFoundException {
        Long applicationId = 1L;
        ResponseEntity<Void> response = loanCreateDocumentsController.createDocuments(applicationId);
        assert response.getStatusCode() == HttpStatus.OK;
        verify(createDocumentsProducerService).sendMessageToKafka(applicationId);
        verify(createDocumentsProducerService, times(1)).sendMessageToKafka(applicationId);
    }
}