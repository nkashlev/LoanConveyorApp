package ru.nkashlev.api_gateway.gateway.controllers.conveyor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.api_gateway.gateway.service.ConveyorFeignClient;
import ru.nkashlev.api_gateway.model.CreditDTO;
import ru.nkashlev.api_gateway.model.ScoringDataDTO;

@RunWith(MockitoJUnitRunner.class)
public class LoanConveyorCalculateControllerTest {

    @InjectMocks
    private LoanConveyorCalculateController loanConveyorCalculateController;

    @Mock
    private ConveyorFeignClient conveyorFeignClient;

    @Test
    public void calculateLoanOffers_ShouldReturnSuccessResponse() {
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        CreditDTO expectedResponse = new CreditDTO();

        Mockito.when(conveyorFeignClient.calculateLoanOffers(Mockito.any(ScoringDataDTO.class)))
                .thenReturn(ResponseEntity.ok(expectedResponse));
        ResponseEntity<CreditDTO> actualResponse =
                loanConveyorCalculateController.calculateLoanOffers(scoringDataDTO);

        Assert.assertEquals(ResponseEntity.ok(expectedResponse), actualResponse);
        Mockito.verify(conveyorFeignClient, Mockito.times(1))
                .calculateLoanOffers(scoringDataDTO);
    }

    @Test
    public void calculateLoanOffers_ShouldReturnErrorResponse() {
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        ResponseEntity<CreditDTO> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        Mockito.when(conveyorFeignClient.calculateLoanOffers(Mockito.any(ScoringDataDTO.class)))
                .thenReturn(expectedResponse);
        ResponseEntity<CreditDTO> actualResponse =
                loanConveyorCalculateController.calculateLoanOffers(scoringDataDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(conveyorFeignClient, Mockito.times(1))
                .calculateLoanOffers(scoringDataDTO);
    }
}