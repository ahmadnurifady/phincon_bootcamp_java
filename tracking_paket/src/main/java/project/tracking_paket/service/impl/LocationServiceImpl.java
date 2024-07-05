package project.tracking_paket.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.LocationDto;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.service.LocationService;

@Service
@Transactional
@Slf4j
public class LocationServiceImpl implements LocationService {



    @Autowired
    LocationRepository repo;

    @Override
    public LocationModel createLocation(LocationDto request) {

        LocationModel location = new LocationModel();
        location.setId(request.getId());
        location.setLocationName(request.getLocationName());
        location.setAddress(request.getAddress());

        repo.save(location);

        return location;
    }

    @Override
    public List<LocationModel> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<LocationModel> findLocationById(String id) {

        var location = repo.findById(id);
        if (location.isEmpty()) {
            log.error("updateCheckpointLocation -> Location not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
        return location;
    }

}
