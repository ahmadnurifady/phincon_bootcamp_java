package project.tracking_paket.model;

import org.springframework.boot.autoconfigure.AutoConfiguration;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "price_per_kg")
    private float pricePerKg;
}
