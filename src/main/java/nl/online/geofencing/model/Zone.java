package nl.online.geofencing.model;

import com.fasterxml.jackson.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author urfan beijlerbeijli on 14-5-18.
 * @day Monday on 9:16
 */
@Entity
@Table(name = "zone")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
/*@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@zone_id", scope = Zone.class)*/
public class Zone implements Serializable {

    private String room;
    private boolean status;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    @ManyToMany(mappedBy = "zones", fetch = FetchType.LAZY)
    private List<Client> clients;

    public Zone() {
    }

    public Zone(String room, boolean status) {
        this.room = room;
        this.status = status;
    }

    public Zone(String room, Date createdAt, Date updatedAt, boolean status, List<Client> clients) {
        this.room = room;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.clients = clients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public List<Client> getClients() {
        return clients;
    }

    @JsonIgnore
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /*@Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", room='" + room + '\'' +
                '}';
    }*/

    @Override
    public String toString(){
        String info = "";

        JSONObject jsonInfo = new JSONObject();
        try {
            jsonInfo.put("room",this.room);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject zone = new JSONObject();
        try {
            zone.put("room", this.getRoom());
            jsonInfo.put("zone", zone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        info = jsonInfo.toString();
        return info;
    }



}
