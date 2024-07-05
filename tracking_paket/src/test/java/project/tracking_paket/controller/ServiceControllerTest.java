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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project.tracking_paket.dto.ServiceDto;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.service.ServiceService;

@ExtendWith(MockitoExtension.class)
public class ServiceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceService service;

    @InjectMocks
    private ServiceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreateServiceController() throws Exception {
        ServiceDto request = new ServiceDto();
        request.setId("service-id");
        request.setServiceName("Test Service");
        request.setPricePerKg(10.0f);

        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setId("service-id");
        serviceModel.setServiceName("Test Service");
        serviceModel.setPricePerKg(10.0f);

        when(service.createService(any(ServiceDto.class))).thenReturn(serviceModel);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/service/post")
                        .contentType("application/json")
                        .content("{\"id\":\"service-id\", \"name\":\"Test Service\", \"pricePerKg\":10.0}"))
                .andExpect(status().isCreated());

        verify(service, times(1)).createService(any(ServiceDto.class));
    }

    @Test
    void testFindAllServiceController() throws Exception {
        ServiceModel service1 = new ServiceModel();
        service1.setId("service-id-1");
        service1.setServiceName("Service 1");
        service1.setPricePerKg(10.0f);

        ServiceModel service2 = new ServiceModel();
        service2.setId("service-id-2");
        service2.setServiceName("Service 2");
        service2.setPricePerKg(15.0f);

        List<ServiceModel> services = Arrays.asList(service1, service2);

        when(service.findAll()).thenReturn(services);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/service/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }

    @Test
    void testFindServiceByIdController() throws Exception {
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setId("service-id");
        serviceModel.setServiceName("Test Service");
        serviceModel.setPricePerKg(10.0f);

        when(service.findServiceById(anyString())).thenReturn(serviceModel);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/service/get")
                        .param("id", "service-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).findServiceById(anyString());
    }

}
