package project.tracking_paket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="deliveryCheckpoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCheckpointModel {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name= "deliveryId")
    private DeliveryModel delivery;

    @ManyToOne
    @JoinColumn(name= "locationId")
    private LocationModel location;
}
