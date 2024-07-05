package project.tracking_paket.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.RecipientDto;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.repository.RecipientRepository;
import project.tracking_paket.service.RecipientService;

@Service
@Transactional
@Slf4j
public class RecipientServiceImpl implements RecipientService {



    @Autowired
    RecipientRepository repo;

    @Override
    public RecipientModel createRecipient(RecipientDto request) {


        RecipientModel recipient = new RecipientModel();
        recipient.setId(UUID.randomUUID().toString());
        recipient.setName(request.getName());
        recipient.setPhoneNumber(request.getPhoneNumber());

        repo.save(recipient);

        return recipient;
    }

    @Override
    public List<RecipientModel> findAll() {
        return repo.findAll();
    }

    @Override
    public RecipientModel findRecipitientById(String id) {

        var location = repo.findById(id);
        if (location.isEmpty()) {
            log.error("Data not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        return location.get();

    }

}
