package project.tracking_paket.service;

import java.util.List;

import project.tracking_paket.dto.RecipientDto;
import project.tracking_paket.model.RecipientModel;


public interface RecipientService {

    RecipientModel createRecipient(RecipientDto request);

    List<RecipientModel> findAll();

    RecipientModel findRecipitientById(String id);


}
