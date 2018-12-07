package ch.heig.gamification.entities;

import io.avalia.gamification.api.model.EventProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EventEntity implements Serializable {

    private String appToken;
    private String userId;
    private String name;

    @OneToMany
    private List<EventPropertiesEntity> properties = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventPropertiesEntity> getProperties() {
        return properties;
    }

    public void setProperties(List<EventPropertiesEntity> properties) {
        this.properties = properties;
    }

}
