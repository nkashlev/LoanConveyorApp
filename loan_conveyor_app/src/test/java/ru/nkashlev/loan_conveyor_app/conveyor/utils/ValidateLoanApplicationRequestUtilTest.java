package ru.nkashlev.loan_conveyor_app.conveyor.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_conveyor_app.conveyor.model.LoanApplicationRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidateLoanApplicationRequestUtilTest {
    @InjectMocks
    private ValidateLoanApplicationRequestUtil validateLoanApplicationRequestUtil;

    @Test
    void validateRequestValid() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO();
        request.setAmount(new BigDecimal("400000"));
        request.setTerm(12L);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("johndoe@example.com");
        request.setBirthdate(LocalDate.of(2000, 1, 1));
        request.setPassportSeries("1234");
        request.setPassportNumber("567890");
        List<String> isValid = validateLoanApplicationRequestUtil.validateRequest(request);
        assertTrue(isValid.isEmpty());
    }

    @Test
    void validateRequestNotValid() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO();
        request.setAmount(new BigDecimal("9000"));
        request.setTerm(5L);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("johndoe@example.com");
        request.setBirthdate(LocalDate.of(2000, 1, 1));
        request.setPassportSeries("1234");
        request.setPassportNumber("567890");
        List<String> isValid = validateLoanApplicationRequestUtil.validateRequest(request);
        assertFalse(isValid.isEmpty());
    }
}