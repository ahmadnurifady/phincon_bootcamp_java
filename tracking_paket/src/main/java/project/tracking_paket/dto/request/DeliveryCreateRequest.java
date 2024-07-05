package project.tracking_paket.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCreateRequest {
    
    private UUID serviceId;
    private UUID packetId;
    private float shippingPrice;
}
