package project.tracking_paket.dummy;

import project.tracking_paket.dto.RecipientDto;
import project.tracking_paket.model.RecipientModel;

public class DummyDataRecipient {

    public static RecipientDto createDummyRecipienDto() {
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setId("recipientId");
        recipientDto.setName("Jane Doe");
        recipientDto.setPhoneNumber("987654321");
        return recipientDto;
    }

    public static RecipientModel createDummyRecipienModel(){
        RecipientModel recipientModel = new RecipientModel();
        recipientModel.setId("recipientId");
        recipientModel.setName("Jane Doe");
        recipientModel.setPhoneNumber("987654321");
        return recipientModel;
    }
}
