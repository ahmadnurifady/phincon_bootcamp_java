package project.tracking_paket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name="location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {
    @Id
    private String id;

    @Column(name="location_name")
    private String locationName;

    private String address;
}
