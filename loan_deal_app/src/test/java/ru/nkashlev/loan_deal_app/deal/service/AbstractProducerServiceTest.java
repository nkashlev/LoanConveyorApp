package ru.nkashlev.loan_deal_app.deal.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.SendSesCodeRequest;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;
@SpringBootTest
class AbstractProducerServiceTest {

    @Mock
    private ApplicationUtil applicationUtil;

    @InjectMocks
    private CheckSendCodeProducerService checkSendCodeProducerService;

    @Test
    public void shouldThrowExceptionWhenStatusIsIncorrect() throws ResourceNotFoundException {
        Long applicationId = 1L;
        Application application = new Application();
        application.setStatus(StatusEnum.CC_DENIED);
        when(applicationUtil.findApplicationById(any(Long.class))).thenReturn(application);

        assertThrows(ResourceNotFoundException.class, () -> checkSendCodeProducerService.sendMessageToKafka(applicationId));
    }

    @Test
    public void shouldThrowExceptionWhenCodeDoesNotMatch() throws ResourceNotFoundException {
        Long applicationId = 1L;
        SendSesCodeRequest request = new SendSesCodeRequest();
        request.setCode(1234L);
        Application application = new Application();
        application.setStatus(StatusEnum.CC_DENIED);
        application.setSesCode(4321L);
        when(applicationUtil.findApplicationById(any(Long.class))).thenReturn(application);
        assertThrows(ResourceNotFoundException.class, () -> checkSendCodeProducerService.sendMessageToKafka(applicationId, request));
    }
}