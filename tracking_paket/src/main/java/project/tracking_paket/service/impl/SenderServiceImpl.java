package project.tracking_paket.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import project.tracking_paket.dto.SenderDto;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.repository.SenderRepository;
import project.tracking_paket.service.SenderService;

@Service
@Transactional
@Slf4j
public class SenderServiceImpl implements SenderService {

    @Autowired
    SenderRepository repo;



    @Override
    public SenderModel createSender(SenderDto request) {


        SenderModel sender = new SenderModel();
        sender.setId(UUID.randomUUID().toString());
        sender.setName(request.getName());
        sender.setPhoneNumber(request.getPhoneNumber());

        repo.save(sender);

        return sender;
    }

    @Override
    public List<SenderModel> findAll() {
        return repo.findAll();
    }

    @Override
    public SenderModel findById(String id) {
        var sender = repo.findById(id);
        if (sender.isEmpty()) {
            log.error("Data not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data not found");
        }

        return sender.get();
    }

}
