package ru.nkashlev.loan_deal_app.deal.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.DOCUMENT_CREATED;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.PREPARE_DOCUMENTS;

@SpringBootTest
class SignDocumentsProducerServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationUtil applicationUtil;

    @InjectMocks
    private SignDocumentsProducerService signDocumentsProducerService;

    @Test
    public void testUpdateApplication() {
        Application application = new Application();
        signDocumentsProducerService.updateApplication(application, 1L);
        verify(applicationUtil, times(1)).updateApplicationStatusHistory(application, DOCUMENT_CREATED, AUTOMATIC);
        verify(applicationRepository, times(1)).save(any(Application.class));
    }

    @Test
    public void testGetBeforeStatus() {
        StatusEnum beforeStatus = signDocumentsProducerService.getBeforeStatus();
        assertEquals(PREPARE_DOCUMENTS, beforeStatus);
    }

    @Test
    public void testGetAfterStatus() {
        StatusEnum afterStatus = signDocumentsProducerService.getAfterStatus();
        assertEquals(DOCUMENT_CREATED, afterStatus);
    }
}

