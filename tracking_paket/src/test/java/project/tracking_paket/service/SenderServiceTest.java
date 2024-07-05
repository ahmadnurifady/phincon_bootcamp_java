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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import project.tracking_paket.dto.SenderDto;
import project.tracking_paket.dummy.SenderDummyData;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.repository.SenderRepository;
import project.tracking_paket.service.impl.SenderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SenderServiceTest {

    @Mock
    private SenderRepository repo;

    @InjectMocks
    private SenderServiceImpl service;

    private SenderDto senderDto;
    private SenderModel senderModel;

    @BeforeEach
    void setUp() {
        senderDto = SenderDummyData.createDummySenderDto();
        senderModel = SenderDummyData.createDumySenderModel();
    }

    @Test
    void testCreateSender() {
        SenderModel result = service.createSender(senderDto);

        lenient().when(repo.save(any(SenderModel.class))).thenReturn(senderModel);

        assertNotNull(result);
        assertEquals(senderDto.getName(), result.getName());
        assertEquals(senderDto.getPhoneNumber(), result.getPhoneNumber());
        verify(repo, times(1)).save(any(SenderModel.class));
    }

    @Test
    void testFindAll() {
        List<SenderModel> senderList = new ArrayList<>();
        senderList.add(senderModel);

        when(repo.findAll()).thenReturn(senderList);

        List<SenderModel> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(senderModel.getName(), result.get(0).getName());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(repo.findById(anyString())).thenReturn(Optional.of(senderModel));

        SenderModel result = service.findById(senderModel.getId());

        assertNotNull(result);
        assertEquals(senderModel.getName(), result.getName());
        verify(repo, times(1)).findById(anyString());
    }

    @Test
    void testFindById_NotFound() {
        when(repo.findById(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            service.findById(senderModel.getId());
        });

        String expectedMessage = "Data not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repo, times(1)).findById(anyString());
    }
}
