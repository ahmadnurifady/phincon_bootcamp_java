package project.tracking_paket.service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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

import project.tracking_paket.dto.request.PackageRequest;
import project.tracking_paket.dto.response.PackageResponse;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.repository.PackageRepository;
import project.tracking_paket.repository.RecipientRepository;
import project.tracking_paket.repository.SenderRepository;
import project.tracking_paket.service.impl.PackageServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {
     @Mock
    private PackageRepository repo;

    @Mock
    private SenderRepository senderRepo;

    @Mock
    private RecipientRepository recipientRepo;

    @Mock
    private LocationRepository locationRepo;

    @InjectMocks
    private PackageServiceImpl packageService;

    private PackageRequest packageRequest;
    private PackageModel packageModel;
    private SenderModel senderModel;
    private RecipientModel recipientModel;
    private LocationModel locationModel;

    @BeforeEach
    void setUp() {
        packageRequest = new PackageRequest();
        packageRequest.setSenderId("sender-id");
        packageRequest.setRecipientId("recipient-id");
        packageRequest.setLocationId("location-id");
        packageRequest.setWeight(10.0f);

        senderModel = new SenderModel();
        senderModel.setId("sender-id");

        recipientModel = new RecipientModel();
        recipientModel.setId("recipient-id");

        locationModel = new LocationModel();
        locationModel.setId("location-id");

        packageModel = new PackageModel();
        packageModel.setId("packageId");
        packageModel.setSender(senderModel);
        packageModel.setRecipient(recipientModel);
        packageModel.setDeliveryDestination(locationModel);
        packageModel.setWeight(10.0f);
    }

    @Test
    void testSave_Success() {
        when(senderRepo.findById("sender-id")).thenReturn(Optional.of(senderModel));
        when(recipientRepo.findById("recipient-id")).thenReturn(Optional.of(recipientModel));
        when(locationRepo.findById("location-id")).thenReturn(Optional.of(locationModel));
        when(repo.save(any(PackageModel.class))).thenReturn(packageModel);

        PackageResponse response = packageService.save(packageRequest);

        assertNotNull(response);
        assertEquals(packageModel.getDeliveryDestination(), response.getDeliveryDestination());
        assertEquals(packageModel.getSender(), response.getSender());
        assertEquals(packageModel.getRecipient(), response.getRecipient());
        assertEquals(packageModel.getWeight(), response.getWeight());

        verify(senderRepo, times(1)).findById("sender-id");
        verify(recipientRepo, times(1)).findById("recipient-id");
        verify(locationRepo, times(1)).findById("location-id");
        verify(repo, times(1)).save(any(PackageModel.class));
    }

    @Test
    void testSave_SenderNotFound() {
        when(senderRepo.findById("sender-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            packageService.save(packageRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Sender not found", exception.getReason());

        verify(senderRepo, times(1)).findById("sender-id");
        verify(recipientRepo, times(0)).findById("recipient-id");
        verify(locationRepo, times(0)).findById("location-id");
        verify(repo, times(0)).save(any(PackageModel.class));
    }

    @Test
    void testSave_RecipientNotFound() {
        when(senderRepo.findById("sender-id")).thenReturn(Optional.of(senderModel));
        when(recipientRepo.findById("recipient-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            packageService.save(packageRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("recipient not found", exception.getReason());

        verify(senderRepo, times(1)).findById("sender-id");
        verify(recipientRepo, times(1)).findById("recipient-id");
        verify(locationRepo, times(0)).findById("location-id");
        verify(repo, times(0)).save(any(PackageModel.class));
    }

    @Test
    void testSave_LocationNotFound() {
        when(senderRepo.findById("sender-id")).thenReturn(Optional.of(senderModel));
        when(recipientRepo.findById("recipient-id")).thenReturn(Optional.of(recipientModel));
        when(locationRepo.findById("location-id")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            packageService.save(packageRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Location not found", exception.getReason());

        verify(senderRepo, times(1)).findById("sender-id");
        verify(recipientRepo, times(1)).findById("recipient-id");
        verify(locationRepo, times(1)).findById("location-id");
        verify(repo, times(0)).save(any(PackageModel.class));
    }

    @Test
    void testFindAll() {
        List<PackageModel> packageList = Arrays.asList(packageModel);

        when(repo.findAll()).thenReturn(packageList);

        List<PackageModel> result = packageService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(packageModel.getId(), result.get(0).getId());

        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindPackageById_Success() {
        when(repo.findById(packageModel.getId())).thenReturn(Optional.of(packageModel));

        PackageModel result = packageService.findPackageById(packageModel.getId());

        assertNotNull(result);
        assertEquals(packageModel.getId(), result.getId());

        verify(repo, times(1)).findById(packageModel.getId());
    }

    @Test
    void testFindPackageById_NotFound() {
        when(repo.findById(packageModel.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            packageService.findPackageById(packageModel.getId());
        });

        verify(repo, times(1)).findById(packageModel.getId());
    }
}
