package project.tracking_paket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SenderDto {

    private String id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    @Pattern(regexp = "^(\\+62|0)\\d{7,14}$", message = "Phone number must start with +62 or 0 and followed by 7 to 14 digits")
    private String phoneNumber;
}
