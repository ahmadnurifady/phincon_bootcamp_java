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
import project.tracking_paket.dto.request.PackageRequest;
import project.tracking_paket.dto.response.PackageResponse;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.service.PackageService;


@RestController
@RequestMapping("/api/package")
public class PackageController {

    @Autowired
    private PackageService service;

    @PostMapping("/post")
    public HttpEntity<PackageResponse> createPackage(@Valid @RequestBody PackageRequest request){
        PackageResponse result = service.save(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<PackageModel> findPackageByIdController(@Valid @PathVariable String id){
        PackageModel result = service.findPackageById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<PackageModel>> findAll(){
        List<PackageModel> result = service.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
