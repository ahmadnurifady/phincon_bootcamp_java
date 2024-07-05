package project.tracking_paket.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.tracking_paket.model.DeliveryCheckpointModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.ServiceModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCheckpointUpdateResponse {

    private UUID id;
    private ServiceModel service;
    private PackageModel packet;
    private float shippingPrice;
    private List<DeliveryCheckpointModel> deliveryCheckpoint;
}
