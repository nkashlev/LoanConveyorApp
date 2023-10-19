package ru.nkashlev.api_gateway.gateway.controllers.application;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.api_gateway.gateway.service.feign.ApplicationFeignClient;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LoanCreateApplicationControllerTest {

    @InjectMocks
    private LoanCreateApplicationController loanCreateApplicationController;

    @Mock
    private ApplicationFeignClient applicationFeignClient;

    @Test
    public void loanApplication_ShouldReturnSuccessResponse() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO();
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        loanOffers.add(new LoanOfferDTO());
        ResponseEntity<List<LoanOfferDTO>> expectedResponse = ResponseEntity.ok(loanOffers);

        Mockito.when(applicationFeignClient.loanApplication(Mockito.any(LoanApplicationRequestDTO.class)))
                .thenReturn(expectedResponse);
        ResponseEntity<List<LoanOfferDTO>> actualResponse =
                loanCreateApplicationController.loanApplication(requestDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(applicationFeignClient, Mockito.times(1))
                .loanApplication(requestDTO);
    }

    @Test
    public void loanApplication_ShouldReturnErrorResponse() {

        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO();
        ResponseEntity<List<LoanOfferDTO>> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        Mockito.when(applicationFeignClient.loanApplication(Mockito.any(LoanApplicationRequestDTO.class)))
                .thenReturn(expectedResponse);
        ResponseEntity<List<LoanOfferDTO>> actualResponse =
                loanCreateApplicationController.loanApplication(requestDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(applicationFeignClient, Mockito.times(1))
                .loanApplication(requestDTO);
    }
}