package project.tracking_paket.dummy;

import project.tracking_paket.dto.LocationDto;
import project.tracking_paket.model.LocationModel;

public class DummyDataLocation {
    public static LocationDto createDummyLocationDto(){
        LocationDto locationDto = new LocationDto();
        locationDto.setId("1");
        locationDto.setLocationName("Location 1");
        locationDto.setAddress("123 Main St");
        return locationDto;
    }

    public static LocationModel createDummyLocationModel(){
        LocationModel locationModel = new LocationModel();
        locationModel.setId("1");
        locationModel.setLocationName("Location 1");
        locationModel.setAddress("123 Main St");
        return locationModel;
    }
}
