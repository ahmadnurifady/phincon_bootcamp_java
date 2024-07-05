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

import jakarta.validation.Valid;
import project.tracking_paket.dto.SenderDto;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.service.SenderService;

@RestController
@RequestMapping("/api/sender")
public class SenderController {
    @Autowired
    SenderService service;

    @PostMapping("/post")
    public HttpEntity<SenderModel> createSenderController(@Valid @RequestBody SenderDto request){
        var result = service.createSender(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<SenderModel>> findAllSenderController(){
        var result = service.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<SenderModel> findSenderByIdController(@Valid @PathVariable String id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
}
