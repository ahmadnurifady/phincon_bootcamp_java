package project.tracking_paket.service;

import java.util.List;

import project.tracking_paket.dto.request.DeliveryRequest;
import project.tracking_paket.dto.response.DeliveryResponse;
import project.tracking_paket.dto.response.PackageResponseFindWithLocation;
import project.tracking_paket.model.DeliveryModel;


public interface DeliveryService {
    DeliveryResponse save(DeliveryRequest pengirimanModel);

    List<DeliveryModel> getAll();

    DeliveryResponse findById(String id);

    List<DeliveryModel> findPacketIsReceive();

    PackageResponseFindWithLocation findPacketWithLocation(String packetId);

    List<PackageResponseFindWithLocation> findPackageDetailWithStatusReceive(String locationName);

    DeliveryResponse updateLocationDelivery(String deliveryId, String locationId);


}
