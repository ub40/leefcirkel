package nl.online.geofencing.service;

import nl.online.geofencing.model.Client;

import java.util.List;

/*@author - Urfan Beijlerbeijli*/

public interface ClientService {

    Client save(Client client);

    List<Client> findAll();

    void update(Client client);

    void delete(Client client);

    void saveAll(List<Client> clientGeo);


/*void saveAllZones(HashSet<Zone> zones);*/

}
