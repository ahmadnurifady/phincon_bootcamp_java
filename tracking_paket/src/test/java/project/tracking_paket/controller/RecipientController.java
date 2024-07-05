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

import project.tracking_paket.dto.RecipientDto;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.service.RecipientService;

@ExtendWith(MockitoExtension.class)
public class RecipientController {

    private MockMvc mockMvc;

    @Mock
    private RecipientService service;

    @InjectMocks
    private RecipientController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreateRecipientController() throws Exception {
        RecipientDto request = new RecipientDto();
        request.setId("recipient-id");
        request.setName("Test Recipient");

        RecipientModel response = new RecipientModel();
        response.setId("recipient-id");
        response.setName("Test Recipient");

        when(service.createRecipient(any(RecipientDto.class))).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/recipient/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(service, times(1)).createRecipient(any(RecipientDto.class));
    }

    @Test
    void testFindAllRecipientController() throws Exception {
        RecipientModel recipient1 = new RecipientModel();
        recipient1.setId("recipient-id-1");
        recipient1.setName("Recipient 1");

        RecipientModel recipient2 = new RecipientModel();
        recipient2.setId("recipient-id-2");
        recipient2.setName("Recipient 2");

        List<RecipientModel> recipients = Arrays.asList(recipient1, recipient2);

        when(service.findAll()).thenReturn(recipients);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/recipient/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }

    @Test
    void testFindByIdController() throws Exception {
        RecipientModel recipient = new RecipientModel();
        recipient.setId("recipient-id");
        recipient.setName("Test Recipient");

        when(service.findRecipitientById(anyString())).thenReturn(recipient);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/recipient/{id}", "recipient-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).findRecipitientById(anyString());
    }

}
