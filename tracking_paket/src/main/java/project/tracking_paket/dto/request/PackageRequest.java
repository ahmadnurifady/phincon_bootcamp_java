package project.tracking_paket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageRequest {

    @NotBlank
    private String senderId;

    @NotBlank
    private String recipientId;

    @NotBlank
    private String locationId;

    @NotNull
    private Float weight;


}
