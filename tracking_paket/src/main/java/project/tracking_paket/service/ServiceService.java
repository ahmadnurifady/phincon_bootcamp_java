package project.tracking_paket.service;

import java.util.List;

import project.tracking_paket.dto.ServiceDto;
import project.tracking_paket.model.ServiceModel;

public interface ServiceService {
    ServiceModel createService(ServiceDto request);

    List<ServiceModel> findAll();

    ServiceModel findServiceById(String id);


}
