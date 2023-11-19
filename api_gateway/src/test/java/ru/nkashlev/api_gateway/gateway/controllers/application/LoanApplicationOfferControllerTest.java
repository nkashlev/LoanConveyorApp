package ru.nkashlev.api_gateway.gateway.controllers.application;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import ru.nkashlev.api_gateway.gateway.service.ApplicationFeignClient;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

@RunWith(MockitoJUnitRunner.class)
public class LoanApplicationOfferControllerTest {
    @Mock
    private ApplicationFeignClient applicationFeignClient;

    @InjectMocks
    private LoanApplicationOfferController loanApplicationOfferController;
    @Test
    public void loanOffer_ShouldReturnSuccessResponse() {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        Mockito.when(applicationFeignClient.loanOffer(Mockito.any(LoanOfferDTO.class)))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse = loanApplicationOfferController.loanOffer(loanOfferDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(applicationFeignClient, Mockito.times(1))
                .loanOffer(loanOfferDTO);
    }

    @Test
    public void loanOffer_ShouldReturnErrorResponse() {

        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        ResponseEntity<Void> expectedResponse = ResponseEntity.badRequest().build();

        Mockito.when(applicationFeignClient.loanOffer(Mockito.any(LoanOfferDTO.class)))
                .thenReturn(expectedResponse);
        ResponseEntity<Void> actualResponse = loanApplicationOfferController.loanOffer(loanOfferDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(applicationFeignClient, Mockito.times(1))
                .loanOffer(loanOfferDTO);
    }
}