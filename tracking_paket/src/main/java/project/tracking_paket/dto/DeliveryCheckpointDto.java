package project.tracking_paket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryCheckpointDto {

    @NotBlank
    private String deliveryId;

    @NotBlank
    private String location;
}
