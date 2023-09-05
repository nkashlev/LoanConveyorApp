package ru.nkashlev.loan_deal_app.deal.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.Credit;
import ru.nkashlev.loan_deal_app.deal.entity.util.CreditStatus;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.CreditDTO;
import ru.nkashlev.loan_deal_app.deal.model.FinishRegistrationRequestDTO;
import ru.nkashlev.loan_deal_app.deal.model.ScoringDataDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.repositories.CreditRepository;
import ru.nkashlev.loan_deal_app.deal.utils.FindIdByApplication;
import ru.nkashlev.loan_deal_app.deal.utils.UpdateApplicationStatusHistory;

import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.APPROVED;
import static ru.nkashlev.loan_deal_app.deal.model.ScoringDataDTO.GenderEnum.*;
import static ru.nkashlev.loan_deal_app.deal.model.ScoringDataDTO.MaritalStatusEnum.*;


@Service
@RequiredArgsConstructor
public class CalculateService {

    private final ApplicationRepository applicationRepository;

    private final ConveyorCalculationClient conveyorCalculationClient;

    private final CreditRepository creditRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(CalculateService.class);


    public void finishRegistration(Long id, FinishRegistrationRequestDTO request) throws ResourceNotFoundException {
        Application application = new FindIdByApplication(applicationRepository).findIdByApplication(id);
        ScoringDataDTO scoringDataDTO = setScoringDTO(new ScoringDataDTO(), application, request);
        Credit credit = saveCredit(application, scoringDataDTO);
        new UpdateApplicationStatusHistory(applicationRepository).updateApplicationStatusHistory(application, APPROVED, AUTOMATIC, credit);
        LOGGER.info("Registration finished for application with ID {}: {}", id, request);
    }

    private Credit saveCredit(Application application, ScoringDataDTO scoringDataDTO) {
        CreditDTO creditDTO = conveyorCalculationClient.calculateLoanOffers(scoringDataDTO);
        Credit credit = new Credit();
        credit.setAmount(creditDTO.getAmount());
        credit.setTerm(creditDTO.getTerm());
        credit.setMonthly_payment(creditDTO.getMonthlyPayment());
        credit.setRate(creditDTO.getRate());
        credit.setPsk(creditDTO.getPsk());
        credit.setPayment_schedule(creditDTO.getPaymentSchedule());
        credit.setInsurance_enable(creditDTO.isIsInsuranceEnabled());
        credit.setSalary_client(creditDTO.isIsSalaryClient());
        credit.setCredit_status(CreditStatus.CALCULATED);
        creditRepository.save(credit);
        LOGGER.info("Credit saved for application with ID {}", application.getApplicationId());
        return credit;
    }

    private ScoringDataDTO setScoringDTO(ScoringDataDTO scoringDataDTO, Application application, FinishRegistrationRequestDTO request) {
        scoringDataDTO.setLastName(application.getClient().getLast_name());
        scoringDataDTO.setFirstName(application.getClient().getFirst_name());
        scoringDataDTO.setMiddleName(application.getClient().getMiddle_name());
        scoringDataDTO.setBirthdate(application.getClient().getBirth_date());
        scoringDataDTO.setPassportSeries(application.getClient().getPassport().getSeries());
        scoringDataDTO.setPassportNumber(application.getClient().getPassport().getNumber());
        setGender(request, scoringDataDTO);
        setMaritalStatus(request, scoringDataDTO);
        scoringDataDTO.setDependentAmount(request.getDependentAmount());
        scoringDataDTO.setPassportIssueDate(request.getPassportIssueDate());
        scoringDataDTO.setPassportIssueBranch(request.getPassportIssueBranch());
        scoringDataDTO.setEmployment(request.getEmployment());
        scoringDataDTO.setAccount(request.getAccount());
        scoringDataDTO.isSalaryClient(application.getAppliedOffer().isIsSalaryClient());
        scoringDataDTO.isInsuranceEnabled(application.getAppliedOffer().isIsInsuranceEnabled());
        scoringDataDTO.setAmount(application.getAppliedOffer().getRequestedAmount());
        scoringDataDTO.setTerm(application.getAppliedOffer().getTerm());
        LOGGER.info("ScoringDataDTO saved");
        return scoringDataDTO;
    }

    private void setGender(FinishRegistrationRequestDTO request, ScoringDataDTO scoringDataDTO) {
        switch (request.getGender()) {
            case MALE -> scoringDataDTO.setGender(MALE);
            case FEMALE -> scoringDataDTO.setGender(FEMALE);
            case NON_BINARY -> scoringDataDTO.setGender(NON_BINARY);
        }
    }

    private void setMaritalStatus(FinishRegistrationRequestDTO request, ScoringDataDTO scoringDataDTO) {
        switch (request.getMaritalStatus()) {
            case SINGLE -> scoringDataDTO.setMaritalStatus(SINGLE);
            case MARRIED -> scoringDataDTO.setMaritalStatus(MARRIED);
            case DIVORCED -> scoringDataDTO.setMaritalStatus(DIVORCED);
            case WIDOWED -> scoringDataDTO.setMaritalStatus(WIDOWED);
        }
    }
}
