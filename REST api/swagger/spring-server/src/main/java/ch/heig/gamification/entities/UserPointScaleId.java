package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPointScaleId implements Serializable {

    private String apiToken = null;
    private String userName = null;
    private String pointScaleName = null;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public UserPointScaleId(){}

    public UserPointScaleId(String apiToken, String userName, String pointScaleName){
        this.apiToken = apiToken;
        this.userName = userName;
        this.pointScaleName = pointScaleName;
    }

    public long getId() {
        return id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPointScaleName() {return pointScaleName;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPointScaleId)) return false;
        UserPointScaleId that = (UserPointScaleId) o;
        return Objects.equals(getApiToken(), that.getApiToken()) &&
                Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getPointScaleName(), that.getPointScaleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApiToken(), getUserName(), getPointScaleName());
    }

}
