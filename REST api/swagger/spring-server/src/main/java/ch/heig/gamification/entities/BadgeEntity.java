package ch.heig.gamification.entities;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class BadgeEntity implements Serializable {

    private String apiToken = null;
    private String name = null;
    private String userId = "";

    public String getId() {
        return apiToken+userId;
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
}
