package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class CompositeId implements Serializable {

    private String apiToken = null;
    private String name = null;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public CompositeId(){}

    public CompositeId(String apiToken, String name){
        this.apiToken = apiToken;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeId)) return false;
        CompositeId that = (CompositeId) o;
        return Objects.equals(getApiToken(), that.getApiToken()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApiToken(), getName());
    }
}
