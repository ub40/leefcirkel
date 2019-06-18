package nl.online.geofencing.service;

import nl.online.geofencing.model.Geo;

import java.util.List;

/*
* @author - Urfan Beijlerbeijli
* */
public interface GeoService {

    List<Geo> findAll();

    void saveAll(Geo geo);

    void delete(Geo geo);

    void deleteAll();

    void saveAll(List<Geo> geos);


}
