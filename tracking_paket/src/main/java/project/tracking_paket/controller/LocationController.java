package project.tracking_paket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.LocationDto;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.service.LocationService;

@RestController
@RequestMapping("/api/location")
@Slf4j
public class LocationController {

    @Autowired
    LocationService service;

    @PostMapping("/post")
    public HttpEntity<LocationModel> createLocationController(@Valid @RequestBody LocationDto request) {
        var result = service.createLocation(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<LocationModel>> findAllLocationController() {
        var result = service.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationModel> findByIdController(@Valid @PathVariable String id) {
        var result = service.findLocationById(id);
        if (result.isEmpty()) {
            log.error("updateCheckpointLocation -> Delivery not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result.get());

    }
}
