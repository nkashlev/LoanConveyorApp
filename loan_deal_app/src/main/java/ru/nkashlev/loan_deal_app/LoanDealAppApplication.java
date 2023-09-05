package ru.nkashlev.loan_deal_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanDealAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanDealAppApplication.class, args);
    }
}
