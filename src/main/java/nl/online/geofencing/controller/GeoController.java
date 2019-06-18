
package nl.online.geofencing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.online.geofencing.exceptions.ResourceNotFoundException;
import nl.online.geofencing.model.*;
import nl.online.geofencing.repository.ClientRepository;
import nl.online.geofencing.repository.ClientsGeoRepository;
import nl.online.geofencing.service.GeoService;
import nl.online.geofencing.service_impl.ClientServiceImpl;
import nl.online.geofencing.service_impl.ClientsGeoServiceImpl;
import nl.online.geofencing.model.Client;
import nl.online.geofencing.model.Geo;
import nl.online.geofencing.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author Urfan Beijlerbeijli
 */

@RestController
@RequestMapping(value = "/maps")
public class GeoController {

    @Autowired
    private GeoService geoService;
    @Autowired
    private ClientsGeoServiceImpl historyImpl;
    @Autowired
    private ClientServiceImpl clientsImpl;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientsGeoRepository clientsGeoRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Geo> listofCoordinates() {
        return geoService.findAll();
    }

    @RequestMapping(value = "/checkinside", method = RequestMethod.POST)
    public List<Client> checkBorders() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        List<Client> clients = clientsImpl.findAll();
        double lat;
        double lng;
        String jsonString = "";

        for (Client client : clients) {
            lng = client.getHistory().getLng();
            lat = client.getHistory().getLat();

            boolean data = checkInside(lng, lat);
            String str = String.valueOf(data);

            if (!data) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    jsonString = mapper.writeValueAsString(client);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON : " + jsonString);
                System.out.println("Begin /POST request!");
                String postUrl = "http://localhost:8080/post";
                ResponseEntity<String> postResponse = restTemplate.postForEntity(postUrl, jsonString, String.class);
                System.out.println("Response for Post Request: " + postResponse.getBody());
            }
        }
        return clients;
    }

    @RequestMapping(value = "/locateusers", method = RequestMethod.GET)
    public List<Client> getUserCordinates() {
        return clientsImpl.findAll();
    }

    @RequestMapping(value = "/clientshistory/{id}", method = RequestMethod.GET)
    public void getClientsHistory(@PathVariable(value = "id") Long id){

        Client cli = clientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client", "id", id));

        cli.setHistory(cli.getHistory());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void save(@RequestBody(required = false) Geo geo) {
        geoService.saveAll(geo);
    }


    @RequestMapping(value = "/deleteAll", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void deleteMap() {
        geoService.deleteAll();
    }

    /**
     * Geofensin algorithme
     *
     * @References: http://stefanbangels.blogspot.nl/2013/10/geo-fencing-sample-code.html
     */
    boolean checkInside(double lng, double lat) {

        List<Line> lines = calculateLines();

        List<Line> intersectionLines = filterIntersectingLines(lines, lat);

        List<Geo> intersectionPoints = calculateIntersectionPoints(intersectionLines, lat);

        sortPointsByX(intersectionPoints);

        return calculateInside(intersectionPoints, lng);
    }

    List<Line> calculateLines() {

        List<Line> results = new LinkedList<>();

        List<Geo> points = geoService.findAll();

        Geo lastPoint = null;
        for (Geo point : points) {

            if (lastPoint != null) {
                results.add(new Line(lastPoint, point));
            }
            lastPoint = point;
        }

        for (int i = 0; i < points.size(); i++) {
            results.add(new Line(lastPoint, points.get(0)));
        }

        return results;
    }

    List<Line> filterIntersectingLines(List<Line> lines, double lat) {
        List<Line> results = new LinkedList<Line>();
        for (Line line : lines) {
            if (isLineIntersectingAtY(line, lat)) {
                results.add(line);
            }
        }
        return results;
    }

    boolean isLineIntersectingAtY(Line line, double lat) {
        double minLat = Math.min(
                line.getFrom().getLat(), line.getTo().getLat());
        double maxLat = Math.max(
                line.getFrom().getLat(), line.getTo().getLat());
        return lat > minLat && lat <= maxLat;
    }

    List<Geo> calculateIntersectionPoints(List<Line> lines, double lat) {
        List<Geo> results = new LinkedList<Geo>();
        for (Line line : lines) {
            double lng = calculateLineXAtY(line, lat);
            results.add(new Geo(lng, lat));
        }
        return results;
    }

    double calculateLineXAtY(Line line, double lat) {
        Geo from = line.getFrom();
        double slope = calculateSlope(line);
        return from.getLat() + (lat - from.getLng()) / slope;
    }

    double calculateSlope(Line line) {
        Geo from = line.getFrom();
        Geo to = line.getTo();
        return (to.getLat() - from.getLat()) / (to.getLng() - from.getLng());
    }

    void sortPointsByX(List<Geo> points) {
        Collections.sort(points, new Comparator<Geo>() {
            public int compare(Geo p1, Geo p2) {
                return Double.compare(p1.getLng(), p2.getLng());
            }
        });
    }

    boolean calculateInside(List<Geo> sortedPoints, double lng) {
        boolean inside = false;

        for (Geo point : sortedPoints) {
            if (lng < point.getLng()) {
                break;
            }
            inside = !inside;
        }

        return inside;
    }


}
