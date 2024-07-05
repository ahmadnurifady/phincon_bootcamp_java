package project.tracking_paket.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.ServiceDto;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.repository.ServiceRepository;
import project.tracking_paket.service.ServiceService;

@Service
@Transactional
@Slf4j
public class ServiceServiceImpl implements ServiceService {



    @Autowired
    ServiceRepository repo;

    @Override
    public ServiceModel createService(ServiceDto request) {


        ServiceModel service = new ServiceModel();
        service.setId(request.getId());
        service.setServiceName(request.getServiceName());
        service.setPricePerKg(request.getPricePerKg());

        repo.save(service);
        return service;

    }

    @Override
    public List<ServiceModel> findAll() {
        return repo.findAll();
    }

    @Override
    public ServiceModel findServiceById(String id) {
        Optional<ServiceModel> service = repo.findById(id);
        if (service.isEmpty()) {
            log.error("Data is not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not found");
        }
        return service.get();

    }

}
