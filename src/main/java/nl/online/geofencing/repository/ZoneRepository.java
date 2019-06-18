package nl.online.geofencing.repository;

import nl.online.geofencing.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author urfan on 17-5-18.
 * @day Thursday on 10:08
 */
@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
//    Client findClientsByMac(@Param("mac") String mac);
}
