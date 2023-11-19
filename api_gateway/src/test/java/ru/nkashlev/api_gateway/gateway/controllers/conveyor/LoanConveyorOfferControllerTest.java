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
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanOfferDTO;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LoanConveyorOfferControllerTest {

    @InjectMocks
    private LoanConveyorOfferController loanConveyorOfferController;

    @Mock
    private ConveyorFeignClient conveyorFeignClient;

    @Test
    public void scoringLoanOffers_ShouldReturnSuccessResponse() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();

        List<LoanOfferDTO> expectedResponse = new ArrayList<>();

        Mockito.when(conveyorFeignClient.scoringLoanOffers(Mockito.any(LoanApplicationRequestDTO.class)))
                .thenReturn(ResponseEntity.ok(expectedResponse));
        ResponseEntity<List<LoanOfferDTO>> actualResponse =
                loanConveyorOfferController.scoringLoanOffers(loanApplicationRequestDTO);

        Assert.assertEquals(ResponseEntity.ok(expectedResponse), actualResponse);
        Mockito.verify(conveyorFeignClient, Mockito.times(1))
                .scoringLoanOffers(loanApplicationRequestDTO);
    }

    @Test
    public void scoringLoanOffers_ShouldReturnErrorResponse() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();
        ResponseEntity<List<LoanOfferDTO>> expectedResponse =
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.when(conveyorFeignClient.scoringLoanOffers(Mockito.any(LoanApplicationRequestDTO.class)))
                .thenReturn(expectedResponse);
        ResponseEntity<List<LoanOfferDTO>> actualResponse =
                loanConveyorOfferController.scoringLoanOffers(loanApplicationRequestDTO);

        Assert.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(conveyorFeignClient, Mockito.times(1))
                .scoringLoanOffers(loanApplicationRequestDTO);
    }
}