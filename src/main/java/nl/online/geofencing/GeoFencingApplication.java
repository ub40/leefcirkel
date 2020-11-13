package nl.online.geofencing;

import nl.online.geofencing.model.Client;
import nl.online.geofencing.model.Zone;
import nl.online.geofencing.repository.ClientRepository;
import nl.online.geofencing.service.ClientService;
import nl.online.geofencing.service_impl.ZoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.WebApplicationInitializer;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Urfan Beijlerbeijli
 *
 * */
@SpringBootApplication
@EnableJpaAuditing
public class GeoFencingApplication extends SpringBootServletInitializer implements WebApplicationInitializer, CommandLineRunner {


	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ClientService client;
	@Autowired
	ZoneServiceImpl zoneService;


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GeoFencingApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GeoFencingApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {

		Client cl = new Client("355661082057274");
		Client cl1 = new Client("123123123123123");

		Zone zone = new Zone("Badkamer", false);
		Zone zone1 = new Zone("Slaapkmer", true);

		List<Zone> zones = new ArrayList<>();
		zones.add(zone);
		zones.add(zone1);

		cl.setZones(zones);
		cl1.setZones(zones);

		clientRepository.save(cl);
		clientRepository.save(cl1);


		List<Client> clients = new ArrayList<>();
		clients.add(cl);
		clients.add(cl1);

		zone.setClients(clients);
		zone1.setClients(clients);

		zoneService.save(zone);
		zoneService.save(zone1);

		List<Client> clientsList = clientRepository.findAll();
		List<Zone> zoneList = zoneService.findAll();

		System.out.println(clientsList.size());
		System.out.println(zoneList.size());


		System.out.println("===================Students info:==================");
		clientsList.forEach(student->System.out.println(student.toString()));

		System.out.println("===================Students info:==================");
		zoneList.forEach(subject->System.out.println(subject.toString()));
	}


}
