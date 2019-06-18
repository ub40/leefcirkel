package nl.online.geofencing.repository;

import nl.online.geofencing.model.ClientsGeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsGeoRepository extends JpaRepository<ClientsGeo, Long> {
}
