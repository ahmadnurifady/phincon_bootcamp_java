package project.tracking_paket.dummy;

import project.tracking_paket.dto.SenderDto;
import project.tracking_paket.model.SenderModel;

public class SenderDummyData {

    public static SenderDto createDummySenderDto() {
        SenderDto senderDto = new SenderDto();
        senderDto.setId("senderId");
        senderDto.setName("John Doe");
        senderDto.setPhoneNumber("0123456789");
        return senderDto;
    }

    public static SenderModel createDumySenderModel() {
        SenderModel senderModel = new SenderModel();
        senderModel.setId("senderId");
        senderModel.setName("John Doe");
        senderModel.setPhoneNumber("0123456789");
        return senderModel;
    }
}
