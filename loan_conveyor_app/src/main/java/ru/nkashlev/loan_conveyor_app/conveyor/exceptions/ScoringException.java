package ru.nkashlev.loan_conveyor_app.conveyor.exceptions;

import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ScoringException extends RuntimeException {
    private List<String> invalidFields;

    public ScoringException(String message) {
        super(message);
    }

    public ScoringException(List<String> invalidFields) {
        super("Invalid loan application, invalid field(s): " + invalidFields);
        this.invalidFields = invalidFields;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }
}
