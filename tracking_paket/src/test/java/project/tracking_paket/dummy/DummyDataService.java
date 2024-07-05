package project.tracking_paket.dummy;

import project.tracking_paket.dto.ServiceDto;
import project.tracking_paket.model.ServiceModel;

public class DummyDataService {

   public static ServiceDto createDummyServiceDto() {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId("serviceId");
        serviceDto.setServiceName("Test Service name");
        serviceDto.setPricePerKg(10.0f);
        return serviceDto;
    }

    public static ServiceModel createDummyServiceModel(){
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setId("serviceId");
        serviceModel.setServiceName("Test Service name");
        serviceModel.setPricePerKg(10.0f);
        return serviceModel;
    }
}
