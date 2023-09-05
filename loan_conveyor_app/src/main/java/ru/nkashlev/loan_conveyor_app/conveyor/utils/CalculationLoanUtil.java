package ru.nkashlev.loan_conveyor_app.conveyor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_conveyor_app.conveyor.model.PaymentScheduleElement;
import ru.nkashlev.loan_conveyor_app.conveyor.model.ScoringDataDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Component
public class CalculationLoanUtil {
    private static Long id = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationLoanUtil.class);

    public BigDecimal evaluateTotalAmount(BigDecimal amount, BigDecimal rate) {
        BigDecimal overpaymentOnLoan = amount.multiply(rate).
                divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
        LOGGER.info("Evaluate totalAmount");
        return amount.add(overpaymentOnLoan);
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Long term, BigDecimal rate) {
        BigDecimal monthlyRate = monthlyRate(rate);
        BigDecimal temp = BigDecimal.ONE.add(monthlyRate).pow(Math.toIntExact(term));
        BigDecimal monthlyPayment = totalAmount.multiply(monthlyRate.multiply(temp)).divide(temp.subtract(BigDecimal.ONE), 2, HALF_UP);
        LOGGER.info("Calculating monthly payment");
        return monthlyPayment;
    }

    public List<PaymentScheduleElement> calculatePaymentSchedule(BigDecimal amount, BigDecimal rate, Long term) {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        BigDecimal remainingDebt = amount;
        BigDecimal monthlyRate = monthlyRate(rate);
        BigDecimal annuityPayment = calculateMonthlyPayment(amount, term, rate);
        BigDecimal totalPayment = calculateTotalPayment(annuityPayment, term);

        LocalDate currentDate = LocalDate.now();
        for (int i = 1; i <= term; i++) {
            BigDecimal interestPayment = remainingDebt.multiply(monthlyRate).setScale(10, RoundingMode.HALF_UP); //  выплата процентов (interestPayment)
            BigDecimal deptPayment = annuityPayment.subtract(interestPayment).setScale(10, RoundingMode.HALF_UP); // оплата долга (deptPayment)
            remainingDebt = remainingDebt.subtract(deptPayment).setScale(10, RoundingMode.HALF_UP); //остаток долг (remainingDebt)

            LocalDate nextMonth = currentDate.plusMonths(i + 1);
            if (nextMonth.isAfter(LocalDate.of(2023, Month.JANUARY, 31))) {
                nextMonth = nextMonth.plusYears(1);
            }

            paymentSchedule.add(new PaymentScheduleElement().
                    number(id++).
                    totalPayment(totalPayment).
                    debtPayment(deptPayment).
                    interestPayment(interestPayment).
                    remainingDebt(remainingDebt).
                    date(nextMonth)
            );
        }
        LOGGER.info("Payment schedule calculated successfully");
        return paymentSchedule;
    }

    public BigDecimal calculatePSK(BigDecimal totalPayment, BigDecimal amount, Long term) {
        LOGGER.info("PSK calculated successfully");
        return totalPayment.divide(amount.subtract(new BigDecimal("1")),
                10, RoundingMode.HALF_UP).divide(new BigDecimal(term), 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public BigDecimal calculateTotalPayment(BigDecimal monthlyPayment, Long term) {
        LOGGER.info("Total payment calculated successfully");
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    public BigDecimal calculateAmountIsIsInsuranceEnabled(ScoringDataDTO request) {
        BigDecimal amount = request.getAmount();
        return request.isIsInsuranceEnabled() ? amount.add(new BigDecimal("100000")) : amount;
    }

    private BigDecimal monthlyRate(BigDecimal rate) {
        return rate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP).divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
    }
}
