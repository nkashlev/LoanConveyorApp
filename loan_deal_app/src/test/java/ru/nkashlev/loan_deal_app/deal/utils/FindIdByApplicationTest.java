package ru.nkashlev.loan_deal_app.deal.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nkashlev.loan_deal_app.deal.entity.Application;
import ru.nkashlev.loan_deal_app.deal.exceptions.ResourceNotFoundException;
import ru.nkashlev.loan_deal_app.deal.repositories.ApplicationRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindIdByApplicationTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private FindIdByApplication findIdByApplication;

    @Test
    public void testFindIdByApplication() throws ResourceNotFoundException {
        Long id = 1L;
        Application application = new Application();
        application.setApplicationId(id);

        when(applicationRepository.findById(id)).thenReturn(Optional.of(application));

        Application result = findIdByApplication.findIdByApplication(id);

        verify(applicationRepository, times(1)).findById(id);
        assertEquals(application, result);
    }

    @Test
    public void testFindIdByApplicationNotFound() {
        Long id = 1L;

        when(applicationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            findIdByApplication.findIdByApplication(id);
        });
    }
}