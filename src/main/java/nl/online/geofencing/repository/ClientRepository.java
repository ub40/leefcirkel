package nl.online.geofencing.repository;

import nl.online.geofencing.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientsByImei(@Param("imei") String imei);
    Client findClientsByMac(@Param("mac") String mac);
}
