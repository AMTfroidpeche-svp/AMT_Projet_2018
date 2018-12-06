package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
@Table(name="POINTSCALE")
public class PointScaleEntity implements Serializable {

    @Column(name="APITOKEN")
    private String apiToken = null;
    @Column(name="NAME")
    private String name = null;
    @Column(name="USERID")
    private String userId = "";
    @Column(name="VALUE")
    private int value;

    @Id
    @Column(name="ID")
    private String id;

    public String getId() {
        return id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.id = apiToken+userId;
        this.apiToken = apiToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.id = apiToken + userId;
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
