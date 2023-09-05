package ru.nkashlev.loan_conveyor_app.conveyor.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nkashlev.loan_conveyor_app.conveyor.model.PaymentScheduleElement;
import ru.nkashlev.loan_conveyor_app.conveyor.model.ScoringDataDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationLoanUtilTest {

    @Test
    void testEvaluateTotalAmount() {
        CalculationLoanUtil util = new CalculationLoanUtil();
        BigDecimal amount = new BigDecimal("100000");
        BigDecimal rate = new BigDecimal("10");
        BigDecimal expected = new BigDecimal("110000");

        CalculationLoanUtil mockUtil = Mockito.spy(util);
        Mockito.doReturn(expected).when(mockUtil).evaluateTotalAmount(amount, rate);

        BigDecimal result = mockUtil.evaluateTotalAmount(amount, rate);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateMonthlyPayment() {
        CalculationLoanUtil util = new CalculationLoanUtil();
        BigDecimal totalAmount = new BigDecimal("110000");
        Long term = 12L;
        BigDecimal rate = new BigDecimal("10");
        BigDecimal expected = new BigDecimal("9733.82");

        CalculationLoanUtil mockUtil = Mockito.spy(util);
        Mockito.doReturn(expected).when(mockUtil).calculateMonthlyPayment(totalAmount, term, rate);

        BigDecimal result = mockUtil.calculateMonthlyPayment(totalAmount, term, rate);
        assertEquals(expected, result);
    }
    @Test
    void testCalculatePaymentSchedule() {
        CalculationLoanUtil util = new CalculationLoanUtil();
        BigDecimal amount = new BigDecimal("100000");
        BigDecimal rate = new BigDecimal("10");
        Long term = 12L;
        List<PaymentScheduleElement> expected = new ArrayList<>();
        expected.add(new PaymentScheduleElement().number(1L).totalPayment(new BigDecimal("116805.84")).debtPayment(new BigDecimal("9733.82")).interestPayment(new BigDecimal("833.33")).remainingDebt(new BigDecimal("90266.18")).date(LocalDate.now().plusMonths(2)));
        expected.add(new PaymentScheduleElement().number(2L).totalPayment(new BigDecimal("116805.84")).debtPayment(new BigDecimal("9836.60")).interestPayment(new BigDecimal("730.55")).remainingDebt(new BigDecimal("80429.58")).date(LocalDate.now().plusMonths(3)));
        CalculationLoanUtil mockUtil = Mockito.spy(util);
        Mockito.doReturn(expected).when(mockUtil).calculatePaymentSchedule(amount, rate, term);

        List<PaymentScheduleElement> result = mockUtil.calculatePaymentSchedule(amount, rate, term);
        assertEquals(expected, result);
    }

    @Test
    void testCalculatePSK() {
        CalculationLoanUtil util = new CalculationLoanUtil();
        BigDecimal totalPayment = new BigDecimal("116805.84");
        BigDecimal amount = new BigDecimal("100000");
        Long term = 12L;
        BigDecimal expected = new BigDecimal("17.34");

        CalculationLoanUtil mockUtil = Mockito.spy(util);
        Mockito.doReturn(expected).when(mockUtil).calculatePSK(totalPayment, amount, term);

        BigDecimal result = mockUtil.calculatePSK(totalPayment, amount, term);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateTotalPayment() {
        CalculationLoanUtil util = new CalculationLoanUtil();
        BigDecimal monthlyPayment = new BigDecimal("9733.82");
        Long term = 12L;
        BigDecimal expected = new BigDecimal("116805.84");

        CalculationLoanUtil mockUtil = Mockito.spy(util);
        Mockito.doReturn(expected).when(mockUtil).calculateTotalPayment(monthlyPayment, term);

        BigDecimal result = mockUtil.calculateTotalPayment(monthlyPayment, term);
        assertEquals(expected, result);
    }
    @Test
    void testCalculateAmountIsIsInsuranceEnabled() {
        CalculationLoanUtil util = new CalculationLoanUtil();
        ScoringDataDTO request = new ScoringDataDTO();
        request.setAmount(new BigDecimal("100000"));
        request.setIsInsuranceEnabled(true);
        BigDecimal expected = new BigDecimal("200000");

        CalculationLoanUtil mockUtil = Mockito.spy(util);
        Mockito.doReturn(expected).when(mockUtil).calculateAmountIsIsInsuranceEnabled(request);

        BigDecimal result = mockUtil.calculateAmountIsIsInsuranceEnabled(request);
        assertEquals(expected, result);
    }

}