package nl.online.geofencing.controller;

import nl.online.geofencing.exceptions.ResourceNotFoundException;
import nl.online.geofencing.model.Client;
import nl.online.geofencing.model.Zone;
import nl.online.geofencing.repository.ClientRepository;
import nl.online.geofencing.repository.ZoneRepository;
import nl.online.geofencing.service_impl.ClientServiceImpl;
import nl.online.geofencing.service_impl.ZoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author urfan on 17-5-18.
 * @day Thursday on 10:21
 */

@RestController
@RequestMapping(value = "/zone")
public class ZoneController {


    @Autowired
    private ZoneServiceImpl zoneServiceImpl;
    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping(value = "/addZone", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public void addZone(@RequestBody Zone zone) {
        List<Client> cli = clientService.findAll();
        Zone db_zone = zoneServiceImpl.save(zone);

        for (Client client : cli) {
            client.getZones().add(db_zone);
        }
        clientService.saveAll(cli);
    }

    @RequestMapping(value = "/zones", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<Zone> zones() {
        return zoneServiceImpl.findAll();
    }

    @RequestMapping(value = "/updateZone/{id}/zone", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateZone(@PathVariable(value = "id") Long id, @RequestBody Zone zone){

        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        List<Zone> listZone = client.getZones();

        for(Zone z : listZone){
            if(z.getId() == zone.getId()){
                z.setStatus(zone.isStatus());
            }
        }

        client.setZones(listZone);
        clientService.save(client);

    }

    @RequestMapping(value = "/deleteZone", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void deleteZone(@RequestBody Zone zone) {
        zoneServiceImpl.delete(zone);
    }



}