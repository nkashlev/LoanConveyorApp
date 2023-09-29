package ru.nkashlev.loan_dossier_app.dossier.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmailConsumeCreateDocumentsServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;

    @Test
    public void testSendEmail() throws Exception {
        String subject = "Test Subject";
        String text = "Test Text";

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(mimeMessage).setSubject(subject);
        doNothing().when(mimeMessage).setText(text);
        doNothing().when(javaMailSender).send(mimeMessage);
    }
}