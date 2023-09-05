package ru.nkashlev.loan_deal_app.deal.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.ChangeTypeEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum;
import static ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO.StatusEnum.PREAPPROVAL;

@ExtendWith(MockitoExtension.class)
class UpdateApplicationStatusHistoryTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private UpdateApplicationStatusHistory updateApplicationStatusHistory;

    @Test
    void testUpdateApplicationStatusHistory() {
        Long id = 1L;
        Application application = new Application();
        application.setApplicationId(id);
        application.setStatus(PREAPPROVAL);
        List<ApplicationStatusHistoryDTO> statusHistoryList = new ArrayList<>();
        ApplicationStatusHistoryDTO statusHistory = new ApplicationStatusHistoryDTO();
        statusHistory.setStatus(StatusEnum.PREAPPROVAL);
        statusHistory.setTime(LocalDate.now());
        statusHistory.setChangeType(ChangeTypeEnum.AUTOMATIC);
        statusHistoryList.add(statusHistory);
        application.setStatusHistory(statusHistoryList);

        when(applicationRepository.save(application)).thenReturn(application);

        updateApplicationStatusHistory.updateApplicationStatusHistory(application, StatusEnum.APPROVED, ChangeTypeEnum.AUTOMATIC);

        verify(applicationRepository).save(application);
    }
}