package ru.nkashlev.loan_conveyor_app.conveyor.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_conveyor_app.conveyor.model.CreditDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.model.PaymentScheduleElement;
import ru.nkashlev.loan_conveyor_app.conveyor.model.ScoringDataDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationLoanUtil;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationRateUtil;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScoringService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoringService.class);

    private final CalculationRateUtil calculationRateUtil;
    private final CalculationLoanUtil calculationLoanUtil;


    public CreditDTO scoringOffer(ScoringDataDTO request) {
        BigDecimal amount = calculationLoanUtil.calculateAmountIsIsInsuranceEnabled(request);
        Long term = request.getTerm();
        BigDecimal rate = calculationRateUtil.calculateRate(request);
        BigDecimal monthlyPayment = calculationLoanUtil.calculateMonthlyPayment(amount, term, rate);
        BigDecimal totalPayment = calculationLoanUtil.calculateTotalPayment(monthlyPayment, term);
        BigDecimal psk = calculationLoanUtil.calculatePSK(totalPayment, amount, term);
        List<PaymentScheduleElement> paymentScheduleElements = calculationLoanUtil.calculatePaymentSchedule(amount, rate, term);
        LOGGER.info("Credit calculated successfully");
        return new CreditDTO().
                rate(rate).
                amount(amount).
                monthlyPayment(monthlyPayment).
                psk(psk).
                term(term).
                paymentSchedule(paymentScheduleElements).
                isInsuranceEnabled(request.isIsInsuranceEnabled()).
                isSalaryClient(request.isIsSalaryClient());
    }
}

