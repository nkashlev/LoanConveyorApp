package ru.nkashlev.loan_dossier_app.dossier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nkashlev.loan_dossier_app.dossier.entity.util.EmailMessage;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmailConsumerFinishRegistrationServiceTest {

    @Mock
    private ObjectMapper jsonMapper;

    @Mock
    private EmailService emailService;

    @Test
    public void testListen() throws Exception {
        String json = "{}";
        EmailMessage emailMessage = new EmailMessage();

        when(jsonMapper.readValue(json, EmailMessage.class)).thenReturn(emailMessage);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
    }
}