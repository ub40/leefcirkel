package nl.online.geofencing.service_impl;

import nl.online.geofencing.model.Geo;
import nl.online.geofencing.repository.GeoRepository;
import nl.online.geofencing.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author - Urfan Beijlerbeijli
* */

@Service
public class GeoServiceImpl implements GeoService {

    @Autowired
    private GeoRepository map_repository;

    @Override
    public List<Geo> findAll() {
        return (List<Geo>) map_repository.findAll();
    }

    @Override
    public void saveAll(Geo geo) {
        map_repository.save(geo);
    }

    @Override
    public void delete(Geo geo) {
        map_repository.deleteAll();
    }

    @Override
    public void deleteAll() {
        map_repository.deleteAll();
    }

    @Override
    public void saveAll(List<Geo> geos) {
        map_repository.saveAll(geos);
    }


}