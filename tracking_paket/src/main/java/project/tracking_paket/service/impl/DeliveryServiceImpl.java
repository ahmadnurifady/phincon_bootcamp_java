package project.tracking_paket.service.impl;

import java.util.ArrayList;
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
import project.tracking_paket.dto.request.DeliveryRequest;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.dto.response.PackageResponseFindWithLocation;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.model.DeliveryModel;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.repository.DeliveryCheckpointRepository;
import project.tracking_paket.repository.DeliveryRepository;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.repository.PackageRepository;
import project.tracking_paket.repository.ServiceRepository;
import project.tracking_paket.service.DeliveryService;

@Service
@Transactional
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository repo;

    @Autowired
    ServiceRepository serviceRepo;

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    LocationRepository locationRepo;

    @Autowired
    DeliveryCheckpointRepository checkpointRepo;


    @Override
    public List<DeliveryModel> getAll() {
        return repo.findAll();
    }

    @Override
    public DeliveryResponse save(DeliveryRequest request) {

        DeliveryModel delivery = new DeliveryModel();
        DeliveryCheckpointModel checkpointModel = new DeliveryCheckpointModel();

        Optional<PackageModel> myPackage = packageRepo.findById(request.getPacketId());
        if (myPackage.isEmpty()) {
            log.error("Package not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Package not found");
        }
        Optional<ServiceModel> myService = serviceRepo.findById(request.getServiceId());
        if (myService.isEmpty()) {
            log.error("Service not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service not found");
        }
        Optional<LocationModel> myLocation = locationRepo.findById(request.getLocationId());
        if (myLocation.isEmpty()) {
            log.error("Location not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found");
        }

        log.info("SETTER DELIVERY");
        delivery.setId(UUID.randomUUID().toString());
        delivery.setPacket(myPackage.get());
        delivery.setService(myService.get());
        delivery.setShippingPrice((myPackage.get().getWeight() * myService.get().getPricePerKg()));
        log.info("CLEAR SETTER DELIVERY");

        log.info("SETTER CHECKPOINT MODEL");
        checkpointModel.setId(UUID.randomUUID().toString());
        checkpointModel.setDelivery(delivery);
        checkpointModel.setLocation(myLocation.get());
        log.info("CLEAR SETTER CHECKPOINT MODEL");

        log.info("SAVE DELIVERY MODEL");
        repo.save(delivery);
        log.info("SAVE CHECKPOINT MODEL {}", checkpointModel.getId());
        checkpointRepo.save(checkpointModel);

        log.info("SETTER DELIVERY ISRECEIVE");
        if (checkpointModel.getLocation().getId().equals(myPackage.get().getDeliveryDestination().getId())) {
            log.info("FLOW CONTROL SET RECEIVE IS TRUE");
            delivery.setIsReceive(true);
        } else {
            log.info("FLOW CONTROL SET RECEIVE IS FALSE");
            delivery.setIsReceive(false);
        }
        log.info("CLEAR SETTER DELIVERY ISRECEIVE");

        var deliveryCheckpoint = Pair.of(checkpointModel.getLocation(), delivery.getIsReceive().toString());

        repo.save(delivery);

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .service(delivery.getService())
                .packet(delivery.getPacket())
                .shippingPrice(delivery.getShippingPrice())
                .deliveryCheckpoint(List.of(deliveryCheckpoint))
                .build();
    }

    @Override
    public DeliveryResponse findById(String id) {

        Optional<DeliveryModel> model = repo.findById(id);
        if (model.isEmpty()) {
            log.error("Delivery not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery not found");
        }

        List<DeliveryCheckpointModel> checkpoint = checkpointRepo.findAll();
        List<Pair<LocationModel, String>> emptyList = new ArrayList<>();

        for (DeliveryCheckpointModel value : checkpoint) {
            Pair<LocationModel, String> pair = Pair.of(value.getLocation(), value.getDelivery().getIsReceive().toString());
            emptyList.add(pair);
        }

        return DeliveryResponse.builder()
                .id(model.get().getId())
                .service(model.get().getService())
                .packet(model.get().getPacket())
                .shippingPrice(model.get().getShippingPrice())
                .deliveryCheckpoint(emptyList)
                .build();
    }

    @Override
    public List<DeliveryModel> findPacketIsReceive() {

        List<DeliveryModel> model = repo.findDeliveryisReceive();

        return model;
    }

    @Override
    public PackageResponseFindWithLocation findPacketWithLocation(String packetId) {
        DeliveryModel delivery = repo.findDeliveryByPacketId(packetId);
        if (delivery == null) {
            log.error("Data not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data not found");
        }

        return PackageResponseFindWithLocation
                .builder()
                .packageDetail(delivery.getPacket())
                .isReceive(delivery.getIsReceive())
                .build();
    }

    @Override
    public List<PackageResponseFindWithLocation> findPackageDetailWithStatusReceive(String locationName) {

        LocationModel location = locationRepo.findLocationByLocationName(locationName);

        List<DeliveryCheckpointModel> checkpoint = checkpointRepo.findAllByLocationId(location.getId());

        List<PackageResponseFindWithLocation> bucketDelivery = new ArrayList<>();

        for (DeliveryCheckpointModel value : checkpoint) {
            PackageResponseFindWithLocation responsePackage = new PackageResponseFindWithLocation();
            responsePackage.setPackageDetail(value.getDelivery().getPacket());
            responsePackage.setIsReceive(value.getDelivery().getIsReceive());

            bucketDelivery.add(responsePackage);
        }

        return bucketDelivery;
    }

    @Override
    public DeliveryResponse updateLocationDelivery(String deliveryId, String locationId) {
        Optional<LocationModel> locationModelUpdate = locationRepo.findById(locationId);
        if (locationModelUpdate.isEmpty()) {
            log.error("locationModelUpdate -> Location not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
        Optional<DeliveryCheckpointModel> checkpointUpdate = checkpointRepo.findByDeliveryId(deliveryId);
        if (checkpointUpdate.isEmpty()) {
            log.error("checkpointUpdate -> checkpoint not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "delivery not found");
        }

        DeliveryCheckpointModel checkpointModel = checkpointUpdate.get();
        checkpointModel.setLocation(locationModelUpdate.get());
        checkpointRepo.save(checkpointUpdate.get());

        Optional<DeliveryModel> delivery = repo.findById(deliveryId);
        if (delivery.isEmpty()) {
            log.error("delivery -> delivery not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "delivery not found");
        }
        if (delivery.get().getPacket().getDeliveryDestination().getId().equals(locationId)) {
            delivery.get().setIsReceive(true);
        }

        repo.save(delivery.get());
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
