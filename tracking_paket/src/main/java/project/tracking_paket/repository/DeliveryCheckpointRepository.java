package project.tracking_paket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.tracking_paket.model.DeliveryCheckpointModel;

@Repository
public interface DeliveryCheckpointRepository extends JpaRepository<DeliveryCheckpointModel, String> {
    Optional<DeliveryCheckpointModel> findByDeliveryId(String id);
    
    List<DeliveryCheckpointModel> findAllByLocationId(String locationId);

    Optional<DeliveryCheckpointModel> findOneByDeliveryIdAndLocationId(String deliveryId, String locationId);

    @Query(value= "SELECT * FROM delivery_checkpoint d WHERE d.delivery_id = ?1 AND d.location_id = ?2 ", nativeQuery=true)
    Optional<DeliveryCheckpointModel> findByDeliveryLocationId(String deliveryId, String locationId);
}
