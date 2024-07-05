package project.tracking_paket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "package")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageModel {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id" )
    private SenderModel sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private RecipientModel recipient;

    @ManyToOne
    @JoinColumn(name = "delivery_destination")
    private LocationModel deliveryDestination;

    @Column(name = "weight")
    private Float weight;
}
