package ru.nkashlev.loan_deal_app.deal.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.SendSesCodeRequest;
import ru.nkashlev.loan_deal_app.deal.service.CheckSendCodeProducerService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LoanCheckSentCodeControllerTest {
    @Mock
    private CheckSendCodeProducerService checkSendCodeProducerService;
    @InjectMocks
    private LoanCheckSentCodeController loanCheckSentCodeController;

    @Test
    void sendSesCode() throws ResourceNotFoundException {
        Long applicationId = 1L;
        SendSesCodeRequest sendSesCodeRequest = new SendSesCodeRequest();
        sendSesCodeRequest.setCode(1234L);

        ResponseEntity<Void> response = loanCheckSentCodeController.sendSesCode(applicationId, sendSesCodeRequest);
        assert response.getStatusCode() == HttpStatus.OK;
        verify(checkSendCodeProducerService).sendMessageToKafka(applicationId, sendSesCodeRequest);
        verify(checkSendCodeProducerService, times(1)).sendMessageToKafka(applicationId, sendSesCodeRequest);
    }
}