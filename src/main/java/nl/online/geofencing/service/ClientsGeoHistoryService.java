package nl.online.geofencing.service;

import nl.online.geofencing.model.ClientsGeo;

import java.util.List;

/**
 * @author Urfan Beijlerbeijli on 6-5-18.
 * @day Sunday on 16:44
 */
public interface ClientsGeoHistoryService {

    List<ClientsGeo> findAll();


    void delete(ClientsGeo clientsGeo);
}
