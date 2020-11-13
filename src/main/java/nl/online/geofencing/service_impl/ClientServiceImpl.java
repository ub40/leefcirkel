package nl.online.geofencing.service_impl;

import nl.online.geofencing.model.Client;
import nl.online.geofencing.repository.ClientRepository;
import nl.online.geofencing.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*@author - Urfan Beijlerbeijli*/

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository lf_repository;

    @Override
    public Client save(Client client) {
       return lf_repository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return lf_repository.findAll();
    }

    @Override
    public void update(Client client) {
        lf_repository.save(client);
    }

    @Override
    public void delete(Client client) {
        lf_repository.delete(client);
    }

    @Override
    public void saveAll(List<Client> clients) {
        lf_repository.saveAll(clients);
    }




}
