package project.tracking_paket.service;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import project.tracking_paket.dto.DeliveryCheckpointDto;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.model.DeliveryModel;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.repository.DeliveryCheckpointRepository;
import project.tracking_paket.repository.DeliveryRepository;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.service.impl.DeliveryCheckpointServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DeliveryCheckpointServiceTest {

    @Mock
    private DeliveryCheckpointRepository repo;

    @Mock
    private DeliveryRepository deliveryRepo;

    @Mock
    private LocationRepository locationRepo;

    @InjectMocks
    private DeliveryCheckpointServiceImpl service;

    private DeliveryCheckpointDto request;
    private DeliveryModel deliveryModel;
    private LocationModel locationModel;

    @BeforeEach
    void setUp() {
        request = new DeliveryCheckpointDto();
        request.setDeliveryId("delivery-id");
        request.setLocation("location-id");

        deliveryModel = new DeliveryModel();
        deliveryModel.setId("delivery-id");

        locationModel = new LocationModel();
        locationModel.setId("location-id");
    }

    @Test
    void testSave_Success() {
        when(deliveryRepo.findById(request.getDeliveryId())).thenReturn(Optional.of(deliveryModel));
        when(locationRepo.findById(request.getLocation())).thenReturn(Optional.of(locationModel));

        DeliveryCheckpointModel checkpointModel = new DeliveryCheckpointModel();
        checkpointModel.setId(UUID.randomUUID().toString());
        checkpointModel.setDelivery(deliveryModel);
        checkpointModel.setLocation(locationModel);

        when(repo.save(any(DeliveryCheckpointModel.class))).thenReturn(checkpointModel);

        DeliveryCheckpointModel response = service.save(request);

        assertNotNull(response);

        verify(deliveryRepo, times(1)).findById(request.getDeliveryId());
        verify(locationRepo, times(1)).findById(request.getLocation());
        verify(repo, times(1)).save(any(DeliveryCheckpointModel.class));
    }

    @Test
    void testSave_DeliveryNotFound() {
        when(deliveryRepo.findById(request.getDeliveryId())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.save(request);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Delivery not found", exception.getReason());

        verify(deliveryRepo, times(1)).findById(request.getDeliveryId());
        verify(locationRepo, times(0)).findById(anyString());
        verify(repo, times(0)).save(any(DeliveryCheckpointModel.class));
    }

    @Test
    void testSave_LocationNotFound() {
        when(deliveryRepo.findById(request.getDeliveryId())).thenReturn(Optional.of(deliveryModel));
        when(locationRepo.findById(request.getLocation())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.save(request);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Location not found", exception.getReason());

        verify(deliveryRepo, times(1)).findById(request.getDeliveryId());
        verify(locationRepo, times(1)).findById(request.getLocation());
        verify(repo, times(0)).save(any(DeliveryCheckpointModel.class));
    }

    @Test
    void testUpdateLocationDelivery_Success() {
        when(locationRepo.findById("location-id")).thenReturn(Optional.of(locationModel));
        when(repo.findByDeliveryId("delivery-id")).thenReturn(Optional.of(new DeliveryCheckpointModel()));
        when(deliveryRepo.findById("delivery-id")).thenReturn(Optional.of(deliveryModel));

        PackageModel packageModel = new PackageModel();
        packageModel.setId("package-id");
        packageModel.setDeliveryDestination(locationModel);

        deliveryModel.setPacket(packageModel);
        deliveryModel.setService(new ServiceModel());

        DeliveryResponse response = service.updateLocationDelivery("delivery-id", "location-id");

        assertNotNull(response);
        assertEquals("delivery-id", response.getId());
        assertEquals(1, response.getDeliveryCheckpoint().size());

        verify(locationRepo, times(1)).findById("location-id");
        verify(repo, times(1)).findByDeliveryId("delivery-id");
        verify(repo, times(1)).save(any(DeliveryCheckpointModel.class));
        verify(deliveryRepo, times(1)).findById("delivery-id");
        verify(deliveryRepo, times(1)).save(any(DeliveryModel.class));
    }

    @Test
    void testUpdateLocationDelivery_LocationNotFound() {
        when(locationRepo.findById("location-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.updateLocationDelivery("delivery-id", "location-id");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Location not found", exception.getReason());

        verify(locationRepo, times(1)).findById("location-id");
        verify(repo, times(0)).findByDeliveryId(anyString());
        verify(repo, times(0)).save(any(DeliveryCheckpointModel.class));
        verify(deliveryRepo, times(0)).findById(anyString());
        verify(deliveryRepo, times(0)).save(any(DeliveryModel.class));
    }

    @Test
    void testUpdateLocationDelivery_DeliveryNotFound() {
        when(locationRepo.findById("location-id")).thenReturn(Optional.of(locationModel));
        when(repo.findByDeliveryId("delivery-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.updateLocationDelivery("delivery-id", "location-id");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("delivery not found", exception.getReason());

        verify(locationRepo, times(1)).findById("location-id");
        verify(repo, times(1)).findByDeliveryId("delivery-id");
        verify(repo, times(0)).save(any(DeliveryCheckpointModel.class));
        verify(deliveryRepo, times(0)).findById(anyString());
        verify(deliveryRepo, times(0)).save(any(DeliveryModel.class));
    }

}
