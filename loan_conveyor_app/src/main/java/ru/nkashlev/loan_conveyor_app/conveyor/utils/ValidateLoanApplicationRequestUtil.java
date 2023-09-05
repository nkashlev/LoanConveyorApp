package ru.nkashlev.loan_conveyor_app.conveyor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_conveyor_app.conveyor.model.LoanApplicationRequestDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateLoanApplicationRequestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateLoanApplicationRequestUtil.class);

    public List<String> validateRequest(LoanApplicationRequestDTO request) {
        List<String> invalidFields = new ArrayList<>();

        if (request.getAmount().intValue() < 10000) {
            invalidFields.add("amount");
        }
        if (request.getTerm() < 6) {
            invalidFields.add("term");
        }
        if (!request.getFirstName().matches("[A-Za-z]{2,30}")) {
            invalidFields.add("firstName");
        }
        if (!request.getLastName().matches("[A-Za-z]{2,30}")) {
            invalidFields.add("lastName");
        }
        if (request.getMiddleName() != null && !request.getMiddleName().matches("[A-Za-z]{2,30}")) {
            invalidFields.add("middleName");
        }
        if (!request.getEmail().matches("[\\w.]{2,50}@[\\w.]{2,20}")) {
            invalidFields.add("email");
        }
        if (!request.getBirthdate().isBefore(LocalDate.now().minusYears(18))) {
            invalidFields.add("birthdate");
        }
        if (!request.getPassportSeries().matches("\\d{4}")) {
            invalidFields.add("passportSeries");
        }
        if (!request.getPassportNumber().matches("\\d{6}")) {
            invalidFields.add("passportNumber");
        }


        if (!invalidFields.isEmpty()) {
            LOGGER.error("Invalid loan application, invalid field(s): {}", invalidFields);
        }
        return invalidFields;
    }
}
