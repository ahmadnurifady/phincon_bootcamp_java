package project.tracking_paket.dto.response;

import java.util.List;

import org.springframework.data.util.Pair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.PackageModel;
import project.tracking_paket.model.ServiceModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponse {

    private String id;
    private ServiceModel service;
    private PackageModel packet;
    private float shippingPrice;
    private List<Pair<LocationModel, String>>  deliveryCheckpoint;
}
