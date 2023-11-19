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
import ru.nkashlev.api_gateway.gateway.service.LoanDealCalculateService;
import ru.nkashlev.api_gateway.model.FinishRegistrationRequestDTO;

@RunWith(MockitoJUnitRunner.class)
public class LoanDealCalculateControllerTest {

    @InjectMocks
    private LoanDealCalculateController loanDealCalculateController;

    @Mock
    private LoanDealCalculateService dealCalculateService;

    @Test
    public void dealCalculateApplicationIdPut_ShouldReturnSuccessResponse() {
        Long applicationId = 1L;
        FinishRegistrationRequestDTO finishRegistrationRequestDTO = new FinishRegistrationRequestDTO();
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        Mockito.when(dealCalculateService.dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse =
                loanDealCalculateController.dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(dealCalculateService, Mockito.times(1))
                .dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO);
    }

    @Test
    public void dealCalculateApplicationIdPut_ShouldReturnErrorResponse() {

        Long applicationId = 1L;
        FinishRegistrationRequestDTO finishRegistrationRequestDTO = new FinishRegistrationRequestDTO();
        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.when(dealCalculateService.dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse =
                loanDealCalculateController.dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(dealCalculateService, Mockito.times(1))
                .dealCalculateApplicationIdPut(applicationId, finishRegistrationRequestDTO);
    }
}