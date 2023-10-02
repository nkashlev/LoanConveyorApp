package ru.nkashlev.loan_deal_app.deal.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import ru.nkashlev.loan_deal_app.deal.entity.util.EmailMessage;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;

import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkaProducerTest {

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Mock
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    @Test
    public void testSendMessage() {
        String topic = "testTopic";
        EmailMessage message = new EmailMessage("test@example.com", 1L, ApplicationStatusHistoryDTO.StatusEnum.PREAPPROVAL);

        kafkaProducer.sendMessage(topic, message);

        verify(kafkaTemplate, times(1)).send(topic, message);

        var future = mock(ListenableFuture.class);
        when(kafkaTemplate.send(any(String.class), any(EmailMessage.class))).thenReturn(future);
    }
}