package project.tracking_paket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.tracking_paket.model.LocationModel;


@Repository
public interface LocationRepository extends JpaRepository<LocationModel, String> {

    @Query(value="SELECT * FROM location l WHERE l.location_name =?1", nativeQuery=true)
    LocationModel findLocationByLocationName(String locationName);
}
