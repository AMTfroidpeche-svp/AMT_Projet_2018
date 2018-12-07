package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
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
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
