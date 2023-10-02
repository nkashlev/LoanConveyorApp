package ru.nkashlev.loan_dossier_app.dossier.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@SpringBootTest
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendEmail(to, subject, text);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
