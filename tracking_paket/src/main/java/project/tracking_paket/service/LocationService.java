package project.tracking_paket.service;


import java.util.List;
import java.util.Optional;

import project.tracking_paket.dto.LocationDto;
import project.tracking_paket.model.LocationModel;

public interface  LocationService {

    LocationModel createLocation(LocationDto request);

    List<LocationModel> findAll();

    Optional<LocationModel> findLocationById(String id);

}
