package ru.nkashlev.loan_conveyor_app.conveyor.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ScoringExceptionHandler {

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<String> handleScoringException(ScoringException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
