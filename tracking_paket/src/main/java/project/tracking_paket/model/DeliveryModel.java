package project.tracking_paket.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryModel {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "service", referencedColumnName = "id")
    private ServiceModel service;

    @ManyToOne
    @JoinColumn(name = "packet", referencedColumnName = "id")
    private PackageModel packet;

    private float shippingPrice;

    private Boolean isReceive;

    @OneToMany(mappedBy = "delivery")
    @JsonIgnore
    private List<DeliveryCheckpointModel> deliveryCheckpoints;
}
