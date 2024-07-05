package project.tracking_paket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.model.SenderModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageResponse {

    private String id;

    private SenderModel sender;

    private RecipientModel recipient;

    private LocationModel deliveryDestination;

    private Float weight;
}
