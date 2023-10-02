package ru.nkashlev.loan_deal_app.deal.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.entity.Credit;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.model.SendSesCodeRequest;
import ru.nkashlev.loan_deal_app.deal.repositories.CreditRepository;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;

@SpringBootTest
class CheckSendCodeProducerServiceTest {
    @Mock
    protected CreditRepository creditRepository;
    @Mock
    protected ApplicationUtil applicationUtil;
    @InjectMocks
    private CheckSendCodeProducerService checkSendCodeProducerService;


    @Test
    public void shouldThrowExceptionWhenApplicationNotFound() throws ResourceNotFoundException {
        Long applicationId = 1L;
        SendSesCodeRequest request = new SendSesCodeRequest();
        request.setCode(1234L);
        when(applicationUtil.findApplicationById(any(Long.class))).thenThrow(new ResourceNotFoundException("Application does not exist or the status is incorrect"));

        assertThrows(ResourceNotFoundException.class, () -> checkSendCodeProducerService.checkAndUpdateApplication(applicationId, request));
    }

    @Test
    public void shouldThrowExceptionWhenCreditSaveFails() throws ResourceNotFoundException {
        Long applicationId = 1L;
        SendSesCodeRequest request = new SendSesCodeRequest();
        request.setCode(1234L);
        Application application = new Application();
        application.setStatus(StatusEnum.DOCUMENT_CREATED);
        application.setSesCode(1234L);
        Credit credit = new Credit();
        application.setCredit(credit);
        when(applicationUtil.findApplicationById(any(Long.class))).thenReturn(application);
        doThrow(new RuntimeException()).when(creditRepository).save(any(Credit.class));

        assertThrows(RuntimeException.class, () -> checkSendCodeProducerService.checkAndUpdateApplication(applicationId, request));
    }
}