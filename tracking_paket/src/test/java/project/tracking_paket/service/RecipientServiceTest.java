package project.tracking_paket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import project.tracking_paket.dto.RecipientDto;
import project.tracking_paket.dummy.DummyDataRecipient;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.repository.RecipientRepository;
import project.tracking_paket.service.impl.RecipientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTest {

    @Mock
    private RecipientRepository repo;

    @InjectMocks
    private RecipientServiceImpl service;

    private RecipientDto recipientDto;
    private RecipientModel recipientModel;

    @BeforeEach
    void setUp() {
        recipientDto = DummyDataRecipient.createDummyRecipienDto();
        recipientModel = DummyDataRecipient.createDummyRecipienModel();
    }

    @Test
    void testCreateRecipient() {
        when(repo.save(any(RecipientModel.class))).thenReturn(recipientModel);

        RecipientModel result = service.createRecipient(recipientDto);

        assertNotNull(result);
        assertEquals(recipientDto.getName(), result.getName());
        assertEquals(recipientDto.getPhoneNumber(), result.getPhoneNumber());
        verify(repo, times(1)).save(any(RecipientModel.class));
    }

    @Test
    void testFindAll() {
        List<RecipientModel> recipientList = new ArrayList<>();
        recipientList.add(recipientModel);

        when(repo.findAll()).thenReturn(recipientList);

        List<RecipientModel> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(recipientModel.getName(), result.get(0).getName());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindRecipientById_Success() {
        when(repo.findById(anyString())).thenReturn(Optional.of(recipientModel));

        RecipientModel result = service.findRecipitientById(recipientModel.getId());

        assertNotNull(result);
        assertEquals(recipientModel.getName(), result.getName());
        verify(repo, times(1)).findById(anyString());
    }

    @Test
    void testFindRecipientById_NotFound() {
        when(repo.findById(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            service.findRecipitientById(recipientModel.getId());
        });

        String expectedMessage = "Data not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repo, times(1)).findById(anyString());
    }
}
