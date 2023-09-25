package ru.nkashlev.loan_dossier_app.dossier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.nkashlev.loan_dossier_app.dossier.entity.Application;
import ru.nkashlev.loan_dossier_app.dossier.entity.util.EmailMessage;
import ru.nkashlev.loan_dossier_app.dossier.model.ScoringDataDTO;
import ru.nkashlev.loan_dossier_app.dossier.repositories.ApplicationRepository;
import ru.nkashlev.loan_dossier_app.dossier.utils.FindIdByApplication;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailConsumeCreateDocumentsService {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumeCreateDocumentsService.class);

    private final ObjectMapper jsonMapper;

    private final EmailService emailService;

    private final ApplicationRepository applicationRepository;

    @KafkaListener(topics = "${spring.kafka.consumer.topic3}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        EmailMessage emailMessage = jsonMapper.readValue(json, EmailMessage.class);
        LOGGER.info("Consumer JSON mapper from topic - conveyor-send-documents {} ", emailMessage);
        Application application = new FindIdByApplication(applicationRepository).findIdByApplication(emailMessage.getApplicationId());
        ScoringDataDTO scoringDataDTO = setScoringDataDTO(application);
        writeToTextFileCreditApplication("credit-application.txt", scoringDataDTO, application);
        writeToTextFileCreditContact("credit-contact.txt", scoringDataDTO, application);
        writeToTextFileCreditPaymentSchedule("credit-payment-schedule.txt", application);
        sendToEmail(emailMessage);
    }

    private ScoringDataDTO setScoringDataDTO(Application application) {
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        scoringDataDTO.setFirstName(application.getClient().getFirst_name());
        scoringDataDTO.setLastName(application.getClient().getLast_name());
        scoringDataDTO.setMiddleName(application.getClient().getMiddle_name());
        scoringDataDTO.setBirthdate(application.getClient().getBirth_date());
        scoringDataDTO.setGender(application.getClient().getGender());
        scoringDataDTO.setPassportNumber(application.getClient().getPassport().getNumber());
        scoringDataDTO.setPassportSeries(application.getClient().getPassport().getSeries());
        scoringDataDTO.setPassportIssueBranch(application.getClient().getPassport().getIssue_branch());
        scoringDataDTO.setPassportIssueDate(application.getClient().getPassport().getIssue_date());
        scoringDataDTO.setMaritalStatus(application.getClient().getMarital_status());
        scoringDataDTO.setDependentAmount(application.getClient().getDependent_amount());
        scoringDataDTO.setAmount(application.getCredit().getAmount());
        scoringDataDTO.setTerm(application.getCredit().getTerm());
        return scoringDataDTO;
    }

    public void writeToTextFileCreditApplication(String filename, ScoringDataDTO scoringDataDTO, Application application) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            String text = String.format(
                    """
                            Credit application № %d from %s
                            Client info:
                               full name: %s %s %s
                               birthday: %s\s
                               gender: %s\s
                               passport: %s %s issued %s branch code %s
                               email: %s\s
                               marital status: %s
                               dependent amount: %d   Employment:
                                  employment status: %s
                                  employer INN (if present): %s
                                  salary: %s
                                  employment position: %s
                                  work experience (total): %d
                                  work experience (current): %d"""

                    , application.getApplicationId(), application.getCreationDate(), scoringDataDTO.getFirstName(),
                    scoringDataDTO.getMiddleName(), scoringDataDTO.getLastName(), scoringDataDTO.getBirthdate(), scoringDataDTO.getGender(),
                    scoringDataDTO.getPassportNumber(), scoringDataDTO.getPassportSeries(), scoringDataDTO.getPassportIssueDate(), scoringDataDTO.getPassportIssueBranch(),
                    application.getClient().getEmail(), scoringDataDTO.getMaritalStatus(), scoringDataDTO.getDependentAmount(),application.getClient().getEmployment().getEmploymentStatus(),
                    application.getClient().getEmployment().getEmployerINN(), application.getClient().getEmployment().getSalary(), application.getClient().getEmployment().getPosition(),
                    application.getClient().getEmployment().getWorkExperienceTotal(), application.getClient().getEmployment().getWorkExperienceCurrent());

            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Write to credit-application.txt successfully!");
    }

    public void writeToTextFileCreditContact(String filename, ScoringDataDTO scoringDataDTO, Application application) {
        try (FileWriter writer = new FileWriter(filename, false)) {

            String text = String.format(
                    """
                            Credit contact № %d from %s
                            Client's full name: %s %s %s
                            Client's passport: %s %s issued %s branch code %s
                            Credit info:
                              amount: %s
                              term: %d
                              monthly payment: %s
                              rate: %s
                              psk: %s
                              services:
                                 insurance: %b
                                 salary: %b"""

                    , application.getApplicationId(), application.getCreationDate(), scoringDataDTO.getFirstName(),
                    scoringDataDTO.getMiddleName(), scoringDataDTO.getLastName(), scoringDataDTO.getPassportNumber(), scoringDataDTO.getPassportSeries(),
                    scoringDataDTO.getPassportIssueDate(), scoringDataDTO.getPassportIssueDate(), scoringDataDTO.getAmount(), scoringDataDTO.getTerm(),
                    application.getCredit().getMonthly_payment(), application.getCredit().getRate(), application.getCredit().getPsk(), scoringDataDTO.isIsInsuranceEnabled(),
                    scoringDataDTO.isIsSalaryClient());

            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Write to credit-contact.txt successfully!");
    }

    public void writeToTextFileCreditPaymentSchedule(String filename, Application application) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            String text = String.format(application.getCredit().getPayment_schedule().toString());
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Write to credit-payment-schedule.txt successfully!");
    }

    private void sendToEmail(EmailMessage emailMessage) throws javax.mail.MessagingException {
        MimeMessage message = emailService.getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String email = emailMessage.getAddress();
        String subject = "Your loan documents";

        helper.setTo(email);
        helper.setSubject(subject);

        FileSystemResource file1 = new FileSystemResource(new File("D:\\programming\\LoanConveyorApp\\credit-application.txt"));
        helper.addAttachment("credit-application.txt", file1);

        FileSystemResource file2 = new FileSystemResource(new File("D:\\programming\\LoanConveyorApp\\credit-contact.txt"));
        helper.addAttachment("credit-contact.txt", file2);

        FileSystemResource file3 = new FileSystemResource(new File("D:\\programming\\LoanConveyorApp\\credit-payment-schedule.txt"));
        helper.addAttachment("credit-payment-schedule.txt", file3);

        String text = String.format("Hello, here it your loan documents for application Nº %d!\n" +
                        "Now you should send singing documents request by the following this link: http://localhost:8080/swagger-ui/index.html#/SingDocuments/singDocuments",
                emailMessage.getApplicationId());
        helper.setText(text);

        try {
            emailService.getJavaMailSender().send(message);
        } catch (MessagingException e) {
            LOGGER.error("Error while sending email: {} subject: {}", e.getMessage(), subject);
        }
        LOGGER.info("Email message sent to {} subject: {}", email, subject);
    }
}
