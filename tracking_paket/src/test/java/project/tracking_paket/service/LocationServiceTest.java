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

import project.tracking_paket.dto.LocationDto;
import project.tracking_paket.dummy.DummyDataLocation;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.service.impl.LocationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository repo;

    @InjectMocks
    private LocationServiceImpl service;

    private LocationDto locationDto;
    private LocationModel locationModel;

    @BeforeEach
    void setUp() {
        locationDto = DummyDataLocation.createDummyLocationDto();

        locationModel = DummyDataLocation.createDummyLocationModel();
    }

    @Test
    void testCreateLocation() {
        when(repo.save(any(LocationModel.class))).thenReturn(locationModel);

        LocationModel result = service.createLocation(locationDto);

        assertNotNull(result);
        assertEquals(locationDto.getId(), result.getId());
        assertEquals(locationDto.getLocationName(), result.getLocationName());
        assertEquals(locationDto.getAddress(), result.getAddress());
        verify(repo, times(1)).save(any(LocationModel.class));
    }

    @Test
    void testFindAll() {
        List<LocationModel> locationList = new ArrayList<>();
        locationList.add(locationModel);

        when(repo.findAll()).thenReturn(locationList);

        List<LocationModel> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(locationModel.getLocationName(), result.get(0).getLocationName());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindLocationById_Success() {
        when(repo.findById(anyString())).thenReturn(Optional.of(locationModel));

        Optional<LocationModel> result = service.findLocationById(locationModel.getId());

        assertTrue(result.isPresent());
        assertEquals(locationModel.getId(), result.get().getId());
        verify(repo, times(1)).findById(anyString());
    }

    @Test
    void testFindLocationById_NotFound() {
        when(repo.findById(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            service.findLocationById(locationModel.getId());
        });

        String expectedMessage = "Location not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repo, times(1)).findById(anyString());
    }
}
