package project.tracking_paket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.tracking_paket.model.PackageModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageResponseFindWithLocation {
    private PackageModel packageDetail;
    private Boolean isReceive;   
}
