package ru.nkashlev.loan_deal_app.deal.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.utils.ApplicationUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum.AUTOMATIC;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.CC_APPROVED;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.PREPARE_DOCUMENTS;

@SpringBootTest
public class CreateDocumentsProducerServiceTest {

    @Mock
    private ApplicationUtil applicationUtil;

    @InjectMocks
    private CreateDocumentsProducerService createDocumentsProducerService;

    @Test
    public void testUpdateApplication() {
        Application application = new Application();
        createDocumentsProducerService.updateApplication(application, 1L);
        verify(applicationUtil, times(1)).updateApplicationStatusHistory(application, PREPARE_DOCUMENTS, AUTOMATIC);
    }

    @Test
    public void testGetBeforeStatus() {
        StatusEnum beforeStatus = createDocumentsProducerService.getBeforeStatus();
        assertEquals(CC_APPROVED, beforeStatus);
    }

    @Test
    public void testGetAfterStatus() {
        StatusEnum afterStatus = createDocumentsProducerService.getAfterStatus();
        assertEquals(PREPARE_DOCUMENTS, afterStatus);
    }
}