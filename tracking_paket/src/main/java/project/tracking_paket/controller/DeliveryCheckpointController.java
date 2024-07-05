package project.tracking_paket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import project.tracking_paket.dto.DeliveryCheckpointDto;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.service.DeliveryCheckpointService;




@RestController
@RequestMapping("api/checkpoint")
public class DeliveryCheckpointController {
    
    @Autowired
    DeliveryCheckpointService service;

    @PostMapping("/post")
    public HttpEntity<DeliveryCheckpointModel> postDeliveryCheckpointController(@Valid @RequestBody DeliveryCheckpointDto request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<DeliveryCheckpointModel>> findAllChecpoint() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping("/updateLocationDelivery")
    public HttpEntity<DeliveryResponse> updateLocationDeliveryController (@Valid @RequestParam String deliveryId, @Valid @RequestParam String locationId) {
        
        return new ResponseEntity<>(service.updateLocationDelivery(deliveryId, locationId), HttpStatus.OK);
    }
    
    
}
