package project.tracking_paket.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.DeliveryCheckpointDto;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.model.DeliveryModel;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.repository.DeliveryCheckpointRepository;
import project.tracking_paket.repository.DeliveryRepository;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.service.DeliveryCheckpointService;
import project.tracking_paket.util.ValidationService;


@Transactional
@Service
@Slf4j
public class DeliveryCheckpointServiceImpl implements DeliveryCheckpointService {

    @Autowired
    DeliveryCheckpointRepository repo;

    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    LocationRepository locationRepo;

    @Autowired
    ValidationService validationService;


    @Override
    public DeliveryCheckpointModel save(DeliveryCheckpointDto request) {
        validationService.validate(request);

        Optional<DeliveryModel> delivery = deliveryRepo.findById(request.getDeliveryId());

        if (delivery.isEmpty()) {
            log.error("Delivery not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found");
        }
        Optional<LocationModel> location = locationRepo.findById(request.getLocation());
        if (location.isEmpty()) {
            log.error("Location not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }

        DeliveryCheckpointModel model = new DeliveryCheckpointModel();
        model.setId(UUID.randomUUID().toString());
        model.setDelivery(delivery.get());
        model.setLocation(location.get());

        repo.save(model);

        return model;

    }

    @Override
    public List<DeliveryCheckpointModel> findAll() {
        return repo.findAll();
    }

    @Override
    public DeliveryResponse updateLocationDelivery(String deliveryId, String locationId) {

        Optional<LocationModel> locationModelUpdate = locationRepo.findById(locationId);
        if (locationModelUpdate.isEmpty()) {
            log.error("locationModelUpdate -> Location not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
        Optional<DeliveryCheckpointModel> checkpointUpdate = repo.findByDeliveryId(deliveryId);
        if (checkpointUpdate.isEmpty()) {
            log.error("checkpointUpdate -> checkpoint not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "delivery not found");
        }

        DeliveryCheckpointModel checkpointModel = checkpointUpdate.get();
        checkpointModel.setLocation(locationModelUpdate.get());
        repo.save(checkpointUpdate.get());

        Optional<DeliveryModel> delivery = deliveryRepo.findById(deliveryId);
        if (delivery.isEmpty()) {
            log.error("delivery -> delivery not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "delivery not found");
        }
        if (delivery.get().getPacket().getDeliveryDestination().getId().equals(locationId)) {
            delivery.get().setIsReceive(true);
        }

        deliveryRepo.save(delivery.get());
        Pair<LocationModel, String> pair = Pair.of(locationModelUpdate.get(), delivery.get().getIsReceive().toString());

        return DeliveryResponse.builder()
                .id(deliveryId)
                .service(delivery.get().getService())
                .packet(delivery.get().getPacket())
                .shippingPrice(delivery.get().getShippingPrice())
                .deliveryCheckpoint(List.of(pair))
                .build();
    }

}
