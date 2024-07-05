package project.tracking_paket.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.request.PackageRequest;
import project.tracking_paket.dto.response.PackageResponse;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.repository.PackageRepository;
import project.tracking_paket.repository.RecipientRepository;
import project.tracking_paket.repository.SenderRepository;
import project.tracking_paket.service.PackageService;


@Service
@Transactional
@Slf4j
public class PackageServiceImpl implements PackageService {

    @Autowired
    PackageRepository repo;

    @Autowired
    SenderRepository senderRepo;

    @Autowired
    RecipientRepository recipientRepo;

    @Autowired
    LocationRepository locationRepo;



    @Override
    public PackageResponse save(PackageRequest request) {


        PackageModel packet = new PackageModel();

        Optional<SenderModel> sender = senderRepo.findById(request.getSenderId());
        if (sender.isEmpty()) {
            log.error("Sender not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender not found");
        }
        Optional<RecipientModel> recipient = recipientRepo.findById(request.getRecipientId());
        if (recipient.isEmpty()) {
            log.error("Recipient not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipient not found");
        }
        Optional<LocationModel> location = locationRepo.findById(request.getLocationId());
        if (location.isEmpty()) {
            log.error("Location not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found");
        }

        packet.setId(UUID.randomUUID().toString());
        packet.setSender(sender.get());
        packet.setRecipient(recipient.get());
        packet.setDeliveryDestination(location.get());
        packet.setWeight(request.getWeight());

        repo.save(packet);

        return PackageResponse.builder()
                .id(packet.getId())
                .deliveryDestination(packet.getDeliveryDestination())
                .sender(sender.get())
                .recipient(recipient.get())
                .weight(packet.getWeight())
                .build();
    }

    @Override
    public List<PackageModel> findAll() {
        return repo.findAll();
    }

    @Override
    public PackageModel findPackageById(String id) {
        return repo.findById(id).get();
    }

}
