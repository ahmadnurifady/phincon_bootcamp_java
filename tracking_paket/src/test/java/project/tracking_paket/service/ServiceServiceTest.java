package project.tracking_paket.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import project.tracking_paket.dto.ServiceDto;
import project.tracking_paket.dummy.DummyDataService;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.repository.ServiceRepository;
import project.tracking_paket.service.impl.ServiceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {

    @Mock
    private ServiceRepository repo;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private ServiceDto serviceDto;
    private ServiceModel serviceModel;

    @BeforeEach
    void setUp() {
        serviceDto = DummyDataService.createDummyServiceDto();
        serviceModel = DummyDataService.createDummyServiceModel();
    }

    @Test
    void testCreateService() {
        when(repo.save(any(ServiceModel.class))).thenReturn(serviceModel);

        ServiceModel result = serviceService.createService(serviceDto);

        assertNotNull(result);
        assertEquals(serviceModel.getId(), result.getId());
        assertEquals(serviceModel.getServiceName(), result.getServiceName());
        assertEquals(serviceModel.getPricePerKg(), result.getPricePerKg());

        verify(repo, times(1)).save(any(ServiceModel.class));
    }

    @Test
    void testFindAll() {
        List<ServiceModel> serviceList = Arrays.asList(serviceModel);

        when(repo.findAll()).thenReturn(serviceList);

        List<ServiceModel> result = serviceService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(serviceModel.getId(), result.get(0).getId());

        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindServiceById_Success() {
        when(repo.findById("1")).thenReturn(Optional.of(serviceModel));

        ServiceModel result = serviceService.findServiceById("1");

        assertNotNull(result);
        assertEquals(serviceModel.getId(), result.getId());
        assertEquals(serviceModel.getServiceName(), result.getServiceName());
        assertEquals(serviceModel.getPricePerKg(), result.getPricePerKg());

        verify(repo, times(1)).findById("1");
    }

    @Test
    void testFindServiceById_NotFound() {
        when(repo.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            serviceService.findServiceById("1");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Data is not found", exception.getReason());

        verify(repo, times(1)).findById("1");
    }
}
