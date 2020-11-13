package nl.online.geofencing.service_impl;

import nl.online.geofencing.model.ClientsGeo;
import nl.online.geofencing.repository.ClientsGeoRepository;
import nl.online.geofencing.service.ClientsGeoHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author urfan beijlerbeijli on 15-5-18.
 * @day Tuesday on 9:36
 */
@Service
public class ClientsGeoServiceImpl implements ClientsGeoHistoryService {

    @Autowired
    private ClientsGeoRepository historyRepository;

    @Override
    public List<ClientsGeo> findAll() {
        return historyRepository.findAll();
    }

    @Override
    public void delete(ClientsGeo clientsGeo) {

    }
}
