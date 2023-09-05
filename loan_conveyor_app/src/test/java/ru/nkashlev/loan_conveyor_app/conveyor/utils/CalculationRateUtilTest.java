package ru.nkashlev.loan_conveyor_app.conveyor.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationRateUtilTest {
    @Mock
    private CalculationRateUtil calculationRateUtil;

    @Test
    public void testCalculateRateWithInsuranceEnabledAndSalaryClient() {
        when(calculationRateUtil.calculateRate(true, true)).thenReturn(new BigDecimal("6"));
        BigDecimal result = calculationRateUtil.calculateRate(true, true);
        assertEquals(new BigDecimal("6"), result);
    }

    @Test
    public void testCalculateRateWithInsuranceDisabledAndSalaryClient() {
        when(calculationRateUtil.calculateRate(false, true)).thenReturn(new BigDecimal("9"));
        BigDecimal result = calculationRateUtil.calculateRate(false, true);
        assertEquals(new BigDecimal("9"), result);
    }

    @Test
    public void testCalculateRateWithInsuranceEnabledAndSalaryNotClient() {
        when(calculationRateUtil.calculateRate(true, false)).thenReturn(new BigDecimal("7"));
        BigDecimal result = calculationRateUtil.calculateRate(true, false);
        assertEquals(new BigDecimal("7"), result);
    }

    @Test
    public void testCalculateRateWithInsuranceDisabledAndSalaryNotClient() {
        when(calculationRateUtil.calculateRate(false, false)).thenReturn(new BigDecimal("10"));
        BigDecimal result = calculationRateUtil.calculateRate(false, false);
        assertEquals(new BigDecimal("10"), result);
    }
}
