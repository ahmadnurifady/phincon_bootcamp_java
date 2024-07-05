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

import project.tracking_paket.dto.DeliveryCheckpointDto;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.service.DeliveryCheckpointService;

@ExtendWith(MockitoExtension.class)
public class DeliveryCheckpointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DeliveryCheckpointService service;

    @InjectMocks
    private DeliveryCheckpointController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testPostDeliveryCheckpointController() throws Exception {
        DeliveryCheckpointDto request = new DeliveryCheckpointDto();

        DeliveryCheckpointModel response = new DeliveryCheckpointModel();

        when(service.save(any(DeliveryCheckpointDto.class))).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/checkpoint/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(any(DeliveryCheckpointDto.class));
    }

    @Test
    void testFindAllCheckpoint() throws Exception {
        DeliveryCheckpointModel checkpoint1 = new DeliveryCheckpointModel();

        DeliveryCheckpointModel checkpoint2 = new DeliveryCheckpointModel();

        List<DeliveryCheckpointModel> checkpoints = Arrays.asList(checkpoint1, checkpoint2);

        when(service.findAll()).thenReturn(checkpoints);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/checkpoint/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }

    @Test
    void testUpdateLocationDeliveryController() throws Exception {
        DeliveryResponse response = new DeliveryResponse();

        when(service.updateLocationDelivery(anyString(), anyString())).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/checkpoint/updateLocationDelivery")
                        .param("deliveryId", "delivery-id")
                        .param("locationId", "location-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).updateLocationDelivery(anyString(), anyString());
    }
}
