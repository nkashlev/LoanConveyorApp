package ru.nkashlev.api_gateway.gateway.controllers.deal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.api_gateway.gateway.service.feign.DealFeignClient;
import ru.nkashlev.api_gateway.model.SendSesCodeRequest;

@RunWith(MockitoJUnitRunner.class)
public class LoanDealCheckSentCodeControllerTest {

    @InjectMocks
    private LoanDealCheckSentCodeController loanDealCheckSentCodeController;

    @Mock
    private DealFeignClient dealFeignClient;

    @Test
    public void sendSesCode_ShouldReturnSuccessResponse() {

        Long applicationId = 1L;
        SendSesCodeRequest sendSesCodeRequest = new SendSesCodeRequest();
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        Mockito.when(dealFeignClient.sendSesCode(applicationId, sendSesCodeRequest))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse =
                loanDealCheckSentCodeController.sendSesCode(applicationId, sendSesCodeRequest);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(dealFeignClient, Mockito.times(1))
                .sendSesCode(applicationId, sendSesCodeRequest);
    }

    @Test
    public void sendSesCode_ShouldReturnErrorResponse() {

        Long applicationId = 1L;
        SendSesCodeRequest sendSesCodeRequest = new SendSesCodeRequest();
        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.when(dealFeignClient.sendSesCode(applicationId, sendSesCodeRequest))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse =
                loanDealCheckSentCodeController.sendSesCode(applicationId, sendSesCodeRequest);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(dealFeignClient, Mockito.times(1))
                .sendSesCode(applicationId, sendSesCodeRequest);
    }
}