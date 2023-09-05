package ru.nkashlev.loan_application_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanApplicationAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanApplicationAppApplication.class, args);
    }
}
