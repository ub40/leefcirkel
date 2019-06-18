package nl.online.geofencing.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Urfan Beijlerbeijli
 * */
@Entity
@Table(name = "clients")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
/*@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@client_id", scope = Client.class)*/
public class Client implements Serializable {


    private String imei;
    private String imsi;
    private String name;
    private String lastname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String mac;

    @ManyToOne(cascade ={CascadeType.ALL, CascadeType.ALL} )
    @JoinColumn(name = "history_id")
    private ClientsGeo history;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clients_zone",
            joinColumns = @JoinColumn(name = "clients_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id", referencedColumnName = "id"))
    private List<Zone> zones;


    public Client() {
    }

    public Client(String imei) {
        this.imei = imei;
    }

    public Client(String mac, List<Zone> zones) {
        this.mac = mac;
        this.zones = zones;
    }

    public Client(String imei, String mac, List<Zone> zones) {
        this.imei = imei;
        this.mac = mac;
        this.zones = zones;
    }

    public Client(String imei, String imsi, String mac, ClientsGeo history) {
        this.imei = imei;
        this.imsi = imsi;
        this.mac = mac;
        this.history = history;
    }

    public Client(String imei, String imsi, String name, String lastname, String mac, ClientsGeo history, List<Zone> zones) {
        this.imei = imei;
        this.imsi = imsi;
        this.name = name;
        this.lastname = lastname;
        this.mac = mac;
        this.history = history;
        this.zones = zones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public void setHistory(ClientsGeo history) {
        this.history = history;
    }

    public ClientsGeo getHistory() {
        return this.history;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

/*    public void addZone(Zone db_zone) {
        this.zones.add(db_zone);
    }*/



    @Override
    public String toString() {
        return "Client{" +
                "imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", id=" + id +
                ", mac='" + mac + '\'' +
                ", history=" + history +
                ", zones=" + zones +
                '}';
    }

}