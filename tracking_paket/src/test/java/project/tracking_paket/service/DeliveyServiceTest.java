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

import project.tracking_paket.dto.request.DeliveryRequest;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.model.DeliveryModel;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.repository.DeliveryCheckpointRepository;
import project.tracking_paket.repository.DeliveryRepository;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.repository.PackageRepository;
import project.tracking_paket.repository.ServiceRepository;
import project.tracking_paket.service.impl.DeliveryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DeliveyServiceTest {

    @Mock
    private DeliveryRepository repo;

    @Mock
    private ServiceRepository serviceRepo;

    @Mock
    private PackageRepository packageRepo;

    @Mock
    private LocationRepository locationRepo;

    @Mock
    private DeliveryCheckpointRepository checkpointRepo;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    private DeliveryRequest deliveryRequest;
    private DeliveryModel deliveryModel;
    private PackageModel packageModel;
    private ServiceModel serviceModel;
    private LocationModel locationModel;
    private DeliveryCheckpointModel checkpointModel;

    @BeforeEach
    void setUp() {
        deliveryRequest = new DeliveryRequest();
        deliveryRequest.setPacketId("package-id");
        deliveryRequest.setServiceId("service-id");
        deliveryRequest.setLocationId("location-id");

        packageModel = new PackageModel();
        packageModel.setId("package-id");
        packageModel.setWeight(10.0f);

        serviceModel = new ServiceModel();
        serviceModel.setId("service-id");
        serviceModel.setPricePerKg(5.0f);

        locationModel = new LocationModel();
        locationModel.setId("location-id");

        deliveryModel = new DeliveryModel();
        deliveryModel.setId("deliveryId");
        deliveryModel.setPacket(packageModel);
        deliveryModel.setService(serviceModel);
        deliveryModel.setShippingPrice(50.0f);
        deliveryModel.setIsReceive(false);

        checkpointModel = new DeliveryCheckpointModel();
        checkpointModel.setId("deliveryCheckpointId");
        checkpointModel.setDelivery(deliveryModel);
        checkpointModel.setLocation(locationModel);
    }

    // @Test
    // void testSave_Success() {
    //     when(packageRepo.findById("package-id")).thenReturn(Optional.of(packageModel));
    //     when(serviceRepo.findById("service-id")).thenReturn(Optional.of(serviceModel));
    //     when(locationRepo.findById("location-id")).thenReturn(Optional.of(locationModel));
    //     when(repo.save(any(DeliveryModel.class))).thenReturn(deliveryModel);
    //     when(checkpointRepo.save(any(DeliveryCheckpointModel.class))).thenReturn(checkpointModel);

    //     DeliveryResponse response = deliveryService.save(deliveryRequest);

    //     assertNotNull(response);
    //     assertEquals(deliveryModel.getService(), response.getService());
    //     assertEquals(deliveryModel.getPacket(), response.getPacket());
    //     assertEquals(deliveryModel.getShippingPrice(), response.getShippingPrice());
    //     assertEquals(1, response.getDeliveryCheckpoint().size());

    //     verify(packageRepo, times(1)).findById("package-id");
    //     verify(serviceRepo, times(1)).findById("service-id");
    //     verify(locationRepo, times(1)).findById("location-id");
    //     verify(repo, times(2)).save(any(DeliveryModel.class));
    //     verify(checkpointRepo, times(1)).save(any(DeliveryCheckpointModel.class));
    // }

    @Test
    void testSave_PackageNotFound() {
        when(packageRepo.findById("package-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deliveryService.save(deliveryRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Package not found", exception.getReason());

        verify(packageRepo, times(1)).findById("package-id");
        verify(serviceRepo, times(0)).findById("service-id");
        verify(locationRepo, times(0)).findById("location-id");
        verify(repo, times(0)).save(any(DeliveryModel.class));
        verify(checkpointRepo, times(0)).save(any(DeliveryCheckpointModel.class));
    }

    @Test
    void testSave_ServiceNotFound() {
        when(packageRepo.findById("package-id")).thenReturn(Optional.of(packageModel));
        when(serviceRepo.findById("service-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deliveryService.save(deliveryRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Service not found", exception.getReason());

        verify(packageRepo, times(1)).findById("package-id");
        verify(serviceRepo, times(1)).findById("service-id");
        verify(locationRepo, times(0)).findById("location-id");
        verify(repo, times(0)).save(any(DeliveryModel.class));
        verify(checkpointRepo, times(0)).save(any(DeliveryCheckpointModel.class));
    }

    @Test
    void testSave_LocationNotFound() {
        when(packageRepo.findById("package-id")).thenReturn(Optional.of(packageModel));
        when(serviceRepo.findById("service-id")).thenReturn(Optional.of(serviceModel));
        when(locationRepo.findById("location-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deliveryService.save(deliveryRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Location not found", exception.getReason());

        verify(packageRepo, times(1)).findById("package-id");
        verify(serviceRepo, times(1)).findById("service-id");
        verify(locationRepo, times(1)).findById("location-id");
        verify(repo, times(0)).save(any(DeliveryModel.class));
        verify(checkpointRepo, times(0)).save(any(DeliveryCheckpointModel.class));
    }

    @Test
    void testGetAll() {
        List<DeliveryModel> deliveryList = Arrays.asList(deliveryModel);

        when(repo.findAll()).thenReturn(deliveryList);

        List<DeliveryModel> result = deliveryService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deliveryModel.getId(), result.get(0).getId());

        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(repo.findById(deliveryModel.getId())).thenReturn(Optional.of(deliveryModel));
        when(checkpointRepo.findAll()).thenReturn(Arrays.asList(checkpointModel));

        DeliveryResponse result = deliveryService.findById(deliveryModel.getId());

        assertNotNull(result);
        assertEquals(deliveryModel.getId(), result.getId());
        assertEquals(deliveryModel.getService(), result.getService());
        assertEquals(deliveryModel.getPacket(), result.getPacket());
        assertEquals(deliveryModel.getShippingPrice(), result.getShippingPrice());
        assertEquals(1, result.getDeliveryCheckpoint().size());

        verify(repo, times(1)).findById(deliveryModel.getId());
        verify(checkpointRepo, times(1)).findAll();
    }

    @Test
    void testFindById_NotFound() {
        when(repo.findById(deliveryModel.getId())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deliveryService.findById(deliveryModel.getId());
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Delivery not found", exception.getReason());

        verify(repo, times(1)).findById(deliveryModel.getId());
        verify(checkpointRepo, times(0)).findAll();
    }
}
