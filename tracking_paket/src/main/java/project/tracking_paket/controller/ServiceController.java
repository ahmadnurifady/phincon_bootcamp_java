package project.tracking_paket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import project.tracking_paket.dto.ServiceDto;
import project.tracking_paket.dto.response.BaseResponse;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.service.ServiceService;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    ServiceService service;

    @PostMapping("/post")
    public BaseResponse<ServiceModel> createServiceController(@Valid @RequestBody ServiceDto request) {
        var result = service.createService(request);
        return new BaseResponse<>(HttpStatus.CREATED, "berhasil membuat service", result);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<ServiceModel>> findAllServiceController() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public HttpEntity<ServiceModel> findServiceByIdController(@Valid @RequestParam String id) {
        var result = service.findServiceById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
