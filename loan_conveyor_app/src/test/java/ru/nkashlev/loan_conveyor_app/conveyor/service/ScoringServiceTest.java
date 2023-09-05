package ru.nkashlev.loan_conveyor_app.conveyor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_conveyor_app.conveyor.model.CreditDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.model.PaymentScheduleElement;
import ru.nkashlev.loan_conveyor_app.conveyor.model.ScoringDataDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationLoanUtil;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationRateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {
    @Mock
    private CalculationRateUtil calculationRateUtil;
    @Mock
    private CalculationLoanUtil calculationLoanUtil;
    @InjectMocks
    private ScoringService scoringService;

    @Test
    void scoringOffer() {
        ScoringDataDTO request = new ScoringDataDTO();
        request.setIsInsuranceEnabled(false);
        BigDecimal InsuranceEnabledTrue = new BigDecimal("500000");

        request.setTerm(18L);
        BigDecimal rate = new BigDecimal("7");
        BigDecimal monthlyPayment = new BigDecimal("2355");
        BigDecimal psk = new BigDecimal("8");

        List<PaymentScheduleElement> list = new ArrayList<>();
        list.add(new PaymentScheduleElement().
                number(1L).
                totalPayment(new BigDecimal("425000")).
                debtPayment(new BigDecimal("26748.94")).
                interestPayment(new BigDecimal("2500")).
                remainingDebt(new BigDecimal("380000")).date(LocalDate.of(2023, Month.JANUARY, 18))
        );

        when(calculationRateUtil.calculateRate(any())).thenReturn(rate);
        when(calculationLoanUtil.calculateMonthlyPayment(any(), any(), any())).thenReturn(monthlyPayment);
        when(calculationLoanUtil.calculatePSK(any(), any(), any())).thenReturn(psk);
        when(calculationLoanUtil.calculatePaymentSchedule(any(), any(), any())).thenReturn(list);
        when(calculationLoanUtil.calculateAmountIsIsInsuranceEnabled(any())).thenReturn(InsuranceEnabledTrue);

        CreditDTO creditDTO = scoringService.scoringOffer(request);

        assertEquals(rate, creditDTO.getRate());
        assertEquals(monthlyPayment, creditDTO.getMonthlyPayment());
        assertEquals(psk, creditDTO.getPsk());
        assertEquals(list, creditDTO.getPaymentSchedule());
        assertEquals(InsuranceEnabledTrue, creditDTO.getAmount());
    }
}