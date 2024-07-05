package project.tracking_paket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.request.DeliveryRequest;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.dto.response.PackageResponseFindWithLocation;
import project.tracking_paket.model.DeliveryModel;
import project.tracking_paket.service.DeliveryService;



@RestController
@RequestMapping("/api/delivery")
@Slf4j
public class DeliveryController {

    @Autowired
    DeliveryService service;

    @PostMapping("/post")
    public HttpEntity<DeliveryResponse> savePengiriman(@RequestBody DeliveryRequest request) {
        DeliveryResponse result = service.save(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<DeliveryModel>> findAll() {

        log.info("START CONTROLLER GET ALL");
        List<DeliveryModel> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<DeliveryResponse> getDeliveryByIdController(@Valid @PathVariable String id) {
        DeliveryResponse result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK
        );
    }

    @GetMapping("/getPacketDetailWithReceive")
    public HttpEntity<List<PackageResponseFindWithLocation>> getPacketDetailWithReceive(@Valid @RequestParam String locationName) {

        return new ResponseEntity<>(service.findPackageDetailWithStatusReceive(locationName), HttpStatus.OK);
    }

    @GetMapping("/packetIsReceived")
    public HttpEntity<List<DeliveryModel>> getPacketIsReceaveController() {
        log.info("START CONTROLLER packetIsReceave");
        
        return new ResponseEntity<>(service.findPacketIsReceive(), HttpStatus.OK);
    }

    @PutMapping("/updateDeliveryLocation")
    public HttpEntity<DeliveryResponse> updateDeliveryLocation(@Valid @RequestParam String deliveryId,@Valid @RequestParam String locationId) {
        
        return new ResponseEntity<>(service.updateLocationDelivery(deliveryId, locationId), HttpStatus.OK);
    }

}
