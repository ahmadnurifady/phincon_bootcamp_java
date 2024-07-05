package project.tracking_paket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDto {

    @NotBlank
    @Size(min = 1, max = 255)
    private String id;

    @NotBlank
    private String serviceName;

    @NotNull
    @Min(1)
    private float pricePerKg;
}
