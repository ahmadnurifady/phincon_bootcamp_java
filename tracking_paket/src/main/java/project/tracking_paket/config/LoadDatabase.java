package project.tracking_paket.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import project.tracking_paket.model.LocationModel;
import project.tracking_paket.model.RecipientModel;
import project.tracking_paket.model.SenderModel;
import project.tracking_paket.model.ServiceModel;
import project.tracking_paket.repository.LocationRepository;
import project.tracking_paket.repository.RecipientRepository;
import project.tracking_paket.repository.SenderRepository;
import project.tracking_paket.repository.ServiceRepository;

public class LoadDatabase {
    
    @Autowired
    private SenderRepository senderRepo;

    @Autowired
    private RecipientRepository recipientRepo;

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private ServiceRepository serviceRepo;

    @Bean
    public CommandLineRunner initDatabase(){
        return args -> {
            SenderModel sender1 = new SenderModel();
            sender1.setId("84de59bb-6441-45e3-b97b-85df52bf99a6");
            sender1.setName("Johan");
            sender1.setPhoneNumber("0123456789");
            SenderModel sender2 = new SenderModel();
            sender2.setId("4c418721-98ae-43f5-8826-40aeaba0d5dd");
            sender2.setName("Jojo");
            sender2.setPhoneNumber("12233445566");
            senderRepo.saveAll(List.of(sender1, sender2));

            RecipientModel recipient1 = new RecipientModel();
            recipient1.setId("a92592be-b92f-4cbc-99cf-4c305676fd11");
            recipient1.setName("Ujang");
            recipient1.setPhoneNumber("019283746565");
            RecipientModel recipient2 = new RecipientModel();
            recipient2.setId("113aeccd-e1e1-43d5-be90-561ef7ba5839");
            recipient2.setName("Yopipiw");
            recipient2.setPhoneNumber("09876543111");
            recipientRepo.saveAll(List.of(recipient1, recipient2));

            LocationModel location1 = new LocationModel();
            location1.setId("GDNG-0001");
            location1.setLocationName("Jakarta");
            location1.setAddress("Jalan di jakarta daerah sana");
            LocationModel location2 = new LocationModel();
            location2.setId("GDNG-0002");
            location2.setLocationName("Tangerang");
            location2.setAddress("Jalan di Tangerang agak lurus");
            locationRepo.saveAll(List.of(location1, location2));

            ServiceModel service1 = new ServiceModel();
            service1.setId("reguler");
            service1.setServiceName("reguler");
            service1.setPricePerKg(100000);
            ServiceModel service2 = new ServiceModel();
            service2.setId("sameday");
            service2.setServiceName("sameday");
            service2.setPricePerKg(50000);
            serviceRepo.saveAll(List.of(service1, service2));


            

            System.out.println("Sample database initialized.");
        };
    }

}
