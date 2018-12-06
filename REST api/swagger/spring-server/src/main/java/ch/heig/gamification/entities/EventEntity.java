package ch.heig.gamification.entities;

import io.avalia.gamification.api.model.EventProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class EventEntity implements Serializable {

    private String appToken;
    private String userId;
    private String name;

    @ElementCollection(targetClass=EventProperties.class)
    private List<EventProperties> properties = new ArrayList<>();

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

    public List<EventProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<EventProperties> properties) {
        this.properties = properties;
    }

}
