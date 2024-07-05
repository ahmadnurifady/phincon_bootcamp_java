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

import project.tracking_paket.dto.request.PackageRequest;
import project.tracking_paket.dto.response.PackageResponse;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.service.PackageService;

@ExtendWith(MockitoExtension.class)
public class PackageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PackageService service;

    @InjectMocks
    private PackageController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreatePackage() throws Exception {
        PackageRequest request = new PackageRequest();
        request.setLocationId("package-id");
        request.setWeight(10.0f);

        PackageResponse response = new PackageResponse();
        response.setId("package-id");
        response.setWeight(10.0f);

        when(service.save(any(PackageRequest.class))).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/package/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service, times(1)).save(any(PackageRequest.class));
    }

    @Test
    void testFindPackageByIdController() throws Exception {
        PackageModel packageModel = new PackageModel();
        packageModel.setId("package-id");
        packageModel.setWeight(10.0f);

        when(service.findPackageById(anyString())).thenReturn(packageModel);

        mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/package/findById/{id}", "package-id"))
                .andExpect(status().isOk());

        verify(service, times(1)).findPackageById(anyString());
    }

    @Test
    void testFindAll() throws Exception {
        PackageModel package1 = new PackageModel();
        package1.setId("package-id-1");
        package1.setWeight(5.0f);

        PackageModel package2 = new PackageModel();
        package2.setId("package-id-2");
        package2.setWeight(7.0f);

        List<PackageModel> packages = Arrays.asList(package1, package2);

        when(service.findAll()).thenReturn(packages);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/package/getAll"))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }
}
