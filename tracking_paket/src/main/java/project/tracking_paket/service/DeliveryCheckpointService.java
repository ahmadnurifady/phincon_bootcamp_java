package project.tracking_paket.service;

import java.util.List;

import project.tracking_paket.dto.DeliveryCheckpointDto;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.model.DeliveryCheckpointModel;


public interface DeliveryCheckpointService {

    DeliveryCheckpointModel save(DeliveryCheckpointDto request);

    List<DeliveryCheckpointModel> findAll();

    DeliveryResponse updateLocationDelivery(String deliveryId, String locationId);

}
