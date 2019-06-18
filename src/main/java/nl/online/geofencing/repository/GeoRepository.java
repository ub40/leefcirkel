package nl.online.geofencing.repository;

import nl.online.geofencing.model.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author urfan beijlerbeijli
 */

@Repository
public interface GeoRepository extends JpaRepository<Geo, Long> {


}
