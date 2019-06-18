package nl.online.geofencing.service;

import nl.online.geofencing.model.Zone;

import java.util.List;

/**
 * @author urfan on 17-5-18.
 * @day Thursday on 10:12
 */
public interface ZoneService {

    List<Zone> findAll();

    Zone save(Zone zone);

    void delete(Zone zone);

    List<Zone> saveAllZones(List<Zone> zones);

}
