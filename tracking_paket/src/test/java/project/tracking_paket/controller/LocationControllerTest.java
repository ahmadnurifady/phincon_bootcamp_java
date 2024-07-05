package project.tracking_paket.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.tracking_paket.dto.LocationDto;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.service.LocationService;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocationService service;

    @InjectMocks
    private LocationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreateLocationController() throws Exception {
        LocationDto request = new LocationDto();
        request.setId("location-id");
        request.setLocationName("Test Location");

        LocationModel response = new LocationModel();
        response.setId("location-id");
        response.setLocationName("Test Location");

        when(service.createLocation(any(LocationDto.class))).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/location/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(service, times(1)).createLocation(any(LocationDto.class));
    }

    @Test
    void testFindAllLocationController() throws Exception {
        LocationModel location1 = new LocationModel();
        location1.setId("location-id-1");
        location1.setLocationName("Location 1");

        LocationModel location2 = new LocationModel();
        location2.setId("location-id-2");
        location2.setLocationName("Location 2");

        List<LocationModel> locations = Arrays.asList(location1, location2);

        when(service.findAll()).thenReturn(locations);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/location/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }

    @Test
    void testFindByIdController_Success() throws Exception {
        LocationModel location = new LocationModel();
        location.setId("location-id");
        location.setLocationName("Test Location");

        when(service.findLocationById(anyString())).thenReturn(Optional.of(location));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/location/{id}", "location-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).findLocationById(anyString());
    }

    @Test
    void testFindByIdController_NotFound() throws Exception {
        when(service.findLocationById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/location/{id}", "location-id"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Delivery not found"));

        verify(service, times(1)).findLocationById(anyString());
    }
}
