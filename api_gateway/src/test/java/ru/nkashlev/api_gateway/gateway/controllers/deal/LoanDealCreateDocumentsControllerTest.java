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
import ru.nkashlev.api_gateway.gateway.service.DealFeignClient;

@RunWith(MockitoJUnitRunner.class)
public class LoanDealCreateDocumentsControllerTest {

    @InjectMocks
    private LoanDealCreateDocumentsController loanDealCreateDocumentsController;

    @Mock
    private DealFeignClient dealFeignClient;

    @Test
    public void createDocuments_ShouldReturnSuccessResponse() {
        Long applicationId = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        Mockito.when(dealFeignClient.createDocuments(applicationId))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse =
                loanDealCreateDocumentsController.createDocuments(applicationId);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(dealFeignClient, Mockito.times(1))
                .createDocuments(applicationId);
    }

    @Test
    public void createDocuments_ShouldReturnErrorResponse() {
        Long applicationId = 1L;
        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.when(dealFeignClient.createDocuments(applicationId))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse =
                loanDealCreateDocumentsController.createDocuments(applicationId);
        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(dealFeignClient, Mockito.times(1))
                .createDocuments(applicationId);
    }
}