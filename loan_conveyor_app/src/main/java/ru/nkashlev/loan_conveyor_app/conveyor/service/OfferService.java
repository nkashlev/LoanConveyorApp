package ru.nkashlev.loan_conveyor_app.conveyor.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_conveyor_app.conveyor.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.model.LoanOfferDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationLoanUtil;
import ru.nkashlev.loan_conveyor_app.conveyor.utils.CalculationRateUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private static Long id = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    private final CalculationLoanUtil calculationLoanUtil;

    private final CalculationRateUtil calculateRate;

    public List<LoanOfferDTO> generateOffers(LoanApplicationRequestDTO request) {
        LOGGER.info("Generate offers");
        return List.of(
                createOffer(false, false, request),
                createOffer(false, true, request),
                createOffer(true, false, request),
                createOffer(true, true, request)
        );
    }

    private LoanOfferDTO createOffer(Boolean isInsuranceEnabled, Boolean isSalaryClient, LoanApplicationRequestDTO request) {
        BigDecimal rate = calculateRate.calculateRate(isInsuranceEnabled, isSalaryClient);
        BigDecimal totalAmount = calculationLoanUtil.evaluateTotalAmount(request.getAmount(), rate);
        BigDecimal monthlyPayment = calculationLoanUtil.calculateMonthlyPayment(request.getAmount(), request.getTerm(), rate);
        LOGGER.info("Create offer № {}", id);
        return new LoanOfferDTO().applicationId(id++)
                .requestedAmount(request.getAmount())
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .rate(rate)
                .monthlyPayment(monthlyPayment);
    }
}
