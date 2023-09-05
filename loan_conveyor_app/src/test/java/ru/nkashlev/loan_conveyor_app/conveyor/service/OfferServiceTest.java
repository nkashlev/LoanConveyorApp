package ru.nkashlev.loan_conveyor_app.conveyor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.nkashlev.loan_conveyor_app.conveyor.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.model.LoanOfferDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationLoanUtil;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationRateUtil;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
    @Mock
    private CalculationRateUtil calculationRateUtil;
    @Mock
    private CalculationLoanUtil calculationLoanUtil;
    @InjectMocks
    private OfferService offerService;

    @Test
    void generateOffers() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO();
        BigDecimal amount = new BigDecimal("100000");
        request.setAmount(amount);
        request.setTerm(18L);

        BigDecimal totalAmountIsnFalse = new BigDecimal("100005");
        BigDecimal totalAmountIsnTrue = new BigDecimal("100002");
        BigDecimal monthlyPayment = new BigDecimal("2355");
        BigDecimal rate = new BigDecimal("10");
        BigDecimal rateIsnSalaryClient = new BigDecimal("9");
        BigDecimal rateIsnInsuranceEnabled = new BigDecimal("7");
        BigDecimal rateIsnInsuranceEnabledIsnInsuranceEnabled = new BigDecimal("6");
        Long term = 18L;

        when(calculationLoanUtil.evaluateTotalAmount(any(), any())).thenReturn(totalAmountIsnFalse, totalAmountIsnFalse,
                totalAmountIsnTrue, totalAmountIsnTrue);

        when(calculationLoanUtil.calculateMonthlyPayment(any(), any(), any())).thenReturn(monthlyPayment);

        when(calculationRateUtil.calculateRate(any(), any())).thenReturn(rate,  rateIsnSalaryClient,
                rateIsnInsuranceEnabled, rateIsnInsuranceEnabledIsnInsuranceEnabled);

        List<LoanOfferDTO> loanOfferDTOList = offerService.generateOffers(request);
        assertEquals(4, loanOfferDTOList.size());

        assertEquals(amount, loanOfferDTOList.get(0).getRequestedAmount());

        assertEquals(term, loanOfferDTOList.get(0).getTerm());

        verify(calculationRateUtil, times(4)).calculateRate(any(), any());
        verify(calculationLoanUtil, times(4)).evaluateTotalAmount(any(), any());
        verify(calculationLoanUtil, times(4)).calculateMonthlyPayment(any(), any(), any());

        assertEquals(totalAmountIsnFalse, loanOfferDTOList.get(0).getTotalAmount());
        assertEquals(totalAmountIsnFalse, loanOfferDTOList.get(1).getTotalAmount());
        assertEquals(totalAmountIsnTrue, loanOfferDTOList.get(2).getTotalAmount());
        assertEquals(totalAmountIsnTrue, loanOfferDTOList.get(3).getTotalAmount());

        assertEquals(monthlyPayment, loanOfferDTOList.get(0).getMonthlyPayment());

        assertEquals(rate, loanOfferDTOList.get(0).getRate());
        assertEquals(rateIsnSalaryClient, loanOfferDTOList.get(1).getRate());
        assertEquals(rateIsnInsuranceEnabled, loanOfferDTOList.get(2).getRate());
        assertEquals(rateIsnInsuranceEnabledIsnInsuranceEnabled, loanOfferDTOList.get(3).getRate());
    }
}