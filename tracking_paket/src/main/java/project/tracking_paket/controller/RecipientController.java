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
import project.tracking_paket.dto.RecipientDto;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.service.RecipientService;

@RestController
@RequestMapping("/api/recipient")
public class RecipientController {

    
    @Autowired
    RecipientService service;


    @PostMapping("/post")
    public HttpEntity<RecipientModel> createRecipientController(@Valid @RequestBody RecipientDto request){
        var result = service.createRecipient(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<RecipientModel>> findAllRecipientController(){
        var result = service.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<RecipientModel> findByIdController(@Valid @PathVariable String id){
        var result = service.findRecipitientById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
