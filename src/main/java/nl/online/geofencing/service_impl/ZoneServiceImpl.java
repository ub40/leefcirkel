package nl.online.geofencing.service_impl;

import nl.online.geofencing.model.Zone;
import nl.online.geofencing.repository.ZoneRepository;
import nl.online.geofencing.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author urfan on 17-5-18.
 * @day Thursday on 10:17
 */
@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Zone save(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public void delete(Zone zone) {
        zoneRepository.delete(zone);
    }

    @Override
    public List<Zone> saveAllZones(List<Zone> zones) {
        return zoneRepository.saveAll(zones);
    }
}
