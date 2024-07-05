package project.tracking_paket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.tracking_paket.model.DeliveryModel;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryModel, String> {

    @Query(value = "SELECT * FROM delivery d WHERE d.is_receive = true", nativeQuery=true)
    List<DeliveryModel> findDeliveryisReceive();

    @Query(value = "SELECT * FROM delivery d WHERE d.packet =?1", nativeQuery=true)
    DeliveryModel findDeliveryByPacketId(String packetId);

}
