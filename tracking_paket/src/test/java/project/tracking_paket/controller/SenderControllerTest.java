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

import project.tracking_paket.dto.SenderDto;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.service.SenderService;

@ExtendWith(MockitoExtension.class)
public class SenderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SenderService service;

    @InjectMocks
    private SenderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreateSenderController() throws Exception {
        SenderDto request = new SenderDto();
        request.setId("sender-id");
        request.setName("Test Sender");

        SenderModel response = new SenderModel();
        response.setId("sender-id");
        response.setName("Test Sender");

        when(service.createSender(any(SenderDto.class))).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/sender/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(service, times(1)).createSender(any(SenderDto.class));
    }

    @Test
    void testFindAllSenderController() throws Exception {
        SenderModel sender1 = new SenderModel();
        sender1.setId("sender-id-1");
        sender1.setName("Sender 1");

        SenderModel sender2 = new SenderModel();
        sender2.setId("sender-id-2");
        sender2.setName("Sender 2");

        List<SenderModel> senders = Arrays.asList(sender1, sender2);

        when(service.findAll()).thenReturn(senders);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/sender/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }

    @Test
    void testFindSenderByIdController() throws Exception {
        SenderModel sender = new SenderModel();
        sender.setId("sender-id");
        sender.setName("Test Sender");

        when(service.findById(anyString())).thenReturn(sender);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/sender/{id}", "sender-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(anyString());
    }

}
