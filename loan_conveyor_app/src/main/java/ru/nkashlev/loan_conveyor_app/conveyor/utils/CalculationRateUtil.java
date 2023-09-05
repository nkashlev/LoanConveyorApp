package ru.nkashlev.loan_conveyor_app.conveyor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nkashlev.loan_conveyor_app.conveyor.exceptions.ScoringException;
import ru.nkashlev.loan_conveyor_app.conveyor.model.EmploymentDTO;
import ru.nkashlev.loan_conveyor_app.conveyor.model.ScoringDataDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
public class CalculationRateUtil {

    @Value("${baseRate}")
    private BigDecimal baseRate;

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationRateUtil.class);

    public BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal rate = baseRate;
        LOGGER.info("baseRate - {}", rate);
        if (isInsuranceEnabled) {
            // Уменьшаем ставку на 3 при наличии страховки
            rate = rate.subtract(new BigDecimal("3"));
        }
        if (isSalaryClient) {
            // Уменьшаем ставку на 1 при наличии зарплатного клиента
            rate = rate.subtract(BigDecimal.ONE);
        }
        LOGGER.info("Calculating rate - {}", rate);
        return rate;
    }

    public BigDecimal calculateRate(ScoringDataDTO request) {
        BigDecimal rate = calculateRate(request.isIsInsuranceEnabled(), request.isIsSalaryClient());
        // правило 1: рабочий статус
        if (request.getEmployment().getEmploymentStatus() == EmploymentDTO.EmploymentStatusEnum.UNEMPLOYED) {
            LOGGER.error("Cannot calculate interest rate for unemployed customers");
            throw new ScoringException("Cannot calculate interest rate for unemployed customers");
        } else if (request.getEmployment().getEmploymentStatus() == EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED) {
            rate = rate.add(new BigDecimal("1"));
        } else if (request.getEmployment().getEmploymentStatus() == EmploymentDTO.EmploymentStatusEnum.BUSINESS_OWNER) {
            rate = rate.add(new BigDecimal("3"));
        }

        // правило 2: позиция на работе
        if (request.getEmployment().getPosition() == EmploymentDTO.PositionEnum.MANAGER) {
            rate = rate.subtract(new BigDecimal("2"));
        } else if (request.getEmployment().getPosition() == EmploymentDTO.PositionEnum.TOP_MANAGER) {
            rate = rate.subtract(new BigDecimal("4"));
        } else if (request.getEmployment().getPosition() == EmploymentDTO.PositionEnum.ENGINEER) {
            rate = rate.subtract(new BigDecimal("3"));
        }

        // правило 3: сумма займа
        BigDecimal monthlyIncome = request.getEmployment().getSalary();
        BigDecimal loanAmount = request.getAmount();
        if (loanAmount.compareTo(monthlyIncome.multiply(new BigDecimal("20"))) > 0) {
            LOGGER.error("Loan amount exceeds 20 times the monthly income");
            throw new ScoringException("Loan amount exceeds 20 times the monthly income");
        }

        // правило 4: семейное положение
        if (request.getMaritalStatus() == ScoringDataDTO.MaritalStatusEnum.MARRIED) {
            rate = rate.subtract(new BigDecimal("3"));
        } else if (request.getMaritalStatus() == ScoringDataDTO.MaritalStatusEnum.DIVORCED) {
            rate = rate.add(new BigDecimal("1"));
        } else if (request.getMaritalStatus() == ScoringDataDTO.MaritalStatusEnum.SINGLE) {
            rate = rate.add(new BigDecimal("1"));
        } else if (request.getMaritalStatus() == ScoringDataDTO.MaritalStatusEnum.WIDOWED) {
            rate = rate.subtract(new BigDecimal("1"));
        }

        // правило 5: количество иждивенцев
        if (request.getDependentAmount() > 1) {
            rate = rate.add(new BigDecimal("1"));
        }

        // правило 6: возраст
        int age = Period.between(request.getBirthdate(), LocalDate.now()).getYears();
        if (age < 20 || age > 61) {
            LOGGER.error("Customer age is not within the acceptable range");
            throw new ScoringException("Customer age is not within the acceptable range");
        }

        // правило 7: пол
        if (request.getGender() == ScoringDataDTO.GenderEnum.FEMALE && age >= 35 && age <= 60) {
            rate = rate.subtract(new BigDecimal("3"));
        } else if (request.getGender() == ScoringDataDTO.GenderEnum.MALE && age >= 30 && age <= 55) {
            rate = rate.subtract(new BigDecimal("3"));
        } else if (request.getGender() == ScoringDataDTO.GenderEnum.NON_BINARY) {
            rate = rate.add(new BigDecimal("3"));
        }

        // правило 8: стаж работы
        if (request.getEmployment().getWorkExperienceCurrent() < 3 || request.getEmployment().getWorkExperienceTotal() < 12) {
            LOGGER.error("Work experience is not sufficient");
            throw new ScoringException("Work experience is not sufficient");
        }
        return rate;
    }
}
