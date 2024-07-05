package project.tracking_paket.controller;

import java.util.Arrays;
import java.util.List;

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

import project.tracking_paket.dto.request.DeliveryRequest;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.dto.response.PackageResponseFindWithLocation;
import project.tracking_paket.model.DeliveryModel;
import project.tracking_paket.service.DeliveryService;

@ExtendWith(MockitoExtension.class)
public class DeliveryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DeliveryService service;

    @InjectMocks
    private DeliveryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSavePengiriman() throws Exception {
        DeliveryRequest request = new DeliveryRequest();

        DeliveryResponse response = new DeliveryResponse();

        when(service.save(any(DeliveryRequest.class))).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/delivery/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service, times(1)).save(any(DeliveryRequest.class));
    }

    @Test
    void testFindAll() throws Exception {
        DeliveryModel delivery1 = new DeliveryModel();

        DeliveryModel delivery2 = new DeliveryModel();

        List<DeliveryModel> deliveries = Arrays.asList(delivery1, delivery2);

        when(service.getAll()).thenReturn(deliveries);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/delivery/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetDeliveryByIdController() throws Exception {
        DeliveryResponse response = new DeliveryResponse();

        when(service.findById(anyString())).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/delivery/{id}", "delivery-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(anyString());
    }

    @Test
    void testGetPacketDetailWithReceive() throws Exception {
        PackageResponseFindWithLocation packageResponse = new PackageResponseFindWithLocation();

        List<PackageResponseFindWithLocation> responseList = Arrays.asList(packageResponse);

        when(service.findPackageDetailWithStatusReceive(anyString())).thenReturn(responseList);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/delivery/getPacketDetailWithReceive").param("locationName", "some-location"))
                .andExpect(status().isOk());

        verify(service, times(1)).findPackageDetailWithStatusReceive(anyString());
    }

    @Test
    void testGetPacketIsReceaveController() throws Exception {
        DeliveryModel delivery1 = new DeliveryModel();

        DeliveryModel delivery2 = new DeliveryModel();

        List<DeliveryModel> deliveries = Arrays.asList(delivery1, delivery2);

        when(service.findPacketIsReceive()).thenReturn(deliveries);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/delivery/packetIsReceived"))
                .andExpect(status().isOk());

        verify(service, times(1)).findPacketIsReceive();
    }

    @Test
    void testUpdateDeliveryLocation() throws Exception {
        DeliveryResponse response = new DeliveryResponse();

        when(service.updateLocationDelivery(anyString(), anyString())).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/delivery/updateDeliveryLocation")
                        .param("deliveryId", "delivery-id")
                        .param("locationId", "location-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).updateLocationDelivery(anyString(), anyString());
    }

}
