package project.tracking_paket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {

    private String id;

    @NotBlank
    @Size(min = 1, max = 150)
    private String locationName;

    @NotBlank
    @Size(min = 1, max = 150)
    private String address;
}
