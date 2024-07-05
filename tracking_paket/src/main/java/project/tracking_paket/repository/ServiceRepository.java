package project.tracking_paket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.tracking_paket.model.ServiceModel;


@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, String>{
}
