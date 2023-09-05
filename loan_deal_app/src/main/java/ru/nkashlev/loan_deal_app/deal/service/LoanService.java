package ru.nkashlev.loan_deal_app.deal.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.Client;
import ru.nkashlev.loan_deal_app.deal.entity.util.Passport;
import ru.nkashlev.loan_deal_app.deal.model.LoanApplicationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final ConveyorOfferClient conveyorOfferClient;
    private final Logger LOGGER = LoggerFactory.getLogger(LoanService.class);

    public List<LoanOfferDTO> createApplication(LoanApplicationRequestDTO request) {
        List<LoanOfferDTO> offers = conveyorOfferClient.calculateLoanOffers(request);
        saveApplication(offers, saveClient(request));
        LOGGER.info("New loan application created for client with email: {}", request.getEmail());
        return offers;
    }

    private Client saveClient(LoanApplicationRequestDTO request) {
        Client client = new Client();
        client.setFirst_name(request.getFirstName());
        client.setMiddle_name(request.getMiddleName());
        client.setLast_name(request.getLastName());
        client.setBirth_date(request.getBirthdate());
        client.setEmail(request.getEmail());
        Passport passport = new Passport();
        passport.setNumber(request.getPassportNumber());
        passport.setSeries(request.getPassportSeries());
        client.setPassport(passport);
        clientRepository.save(client);
        LOGGER.info("New client created with email: {}", request.getEmail());
        return client;
    }

    private void saveApplication(List<LoanOfferDTO> offers, Client client) {
        for (LoanOfferDTO loanOffer : offers) {
            Application application = new Application();
            application.setClient(client);
            applicationRepository.save(application);
            loanOffer.setApplicationId(application.getApplicationId());
            LOGGER.info("New application saved with ID: {}", application.getApplicationId());
        }
    }
}
