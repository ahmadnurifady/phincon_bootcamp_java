package project.tracking_paket.service;

import java.util.List;

import project.tracking_paket.dto.SenderDto;
import project.tracking_paket.model.SenderModel;

public interface SenderService {
    SenderModel createSender(SenderDto request);

    List<SenderModel> findAll();

    SenderModel findById(String id);


}
