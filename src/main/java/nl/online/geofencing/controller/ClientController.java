package nl.online.geofencing.controller;

import nl.online.geofencing.exceptions.ResourceNotFoundException;
import nl.online.geofencing.model.Client;
import nl.online.geofencing.model.ClientsGeo;
import nl.online.geofencing.repository.ClientRepository;
import nl.online.geofencing.repository.ClientsGeoRepository;
import nl.online.geofencing.repository.ZoneRepository;
import nl.online.geofencing.service.ClientService;
import nl.online.geofencing.service_impl.ZoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author - Urfan Beijlerbeijli
 * */
@RestController
@RequestMapping(value = "/leefcirkel")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientsGeoRepository clientsGeoRepository;
    @Autowired
    private ZoneServiceImpl zoneServiceImpl;
    @Autowired
    private ZoneRepository zoneRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveUser(@RequestBody Client client){
        System.out.println("/POST request: " + client.toString());
        clientService.save(client);
        return "success";
    }

    @RequestMapping(value = "/getListOfUsers", method = RequestMethod.GET)
    public List<Client> getListOfUsers(){
         return clientService.findAll();
    }

    @RequestMapping(value = "/gps/update/{imei}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public Client updateGPS(@PathVariable(value = "imei") String imei, @RequestBody Client clients){
                Client client = clientRepository.findClientsByImei(imei);
                    client.setImei(clients.getImei());
                    client.setImsi(clients.getImsi());
                    client.setMac(clients.getMac());
                    client.setHistory(clients.getHistory());

                Client updateClient = clientRepository.save(client);

                return updateClient;
    }


    @RequestMapping(value = "/ble/update/{mac}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public Client updateBLE(@PathVariable(value = "mac") String mac, @RequestBody Client clients){
        Client client = clientRepository.findClientsByMac(mac);
            client.setMac(clients.getMac());
            client.setZones(clients.getZones());

            Client updateClient = clientRepository.save(client);

            return updateClient;
    }

    @RequestMapping(value = "/add/{id}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ClientsGeo updateGEO(@PathVariable(value = "id") Long id, @Valid @RequestBody ClientsGeo geos){

        ClientsGeo clientsGeo = clientsGeoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Geo", "ID", id));
        clientsGeo.setId(geos.getId());
        clientsGeo.setLat(geos.getLat());
        clientsGeo.setLng(geos.getLng());

        ClientsGeo addGeo = clientsGeoRepository.save(clientsGeo);
        return addGeo;
    }



    @RequestMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void removeUser(@RequestBody Client client){
        clientService.delete(client);
    }





}
