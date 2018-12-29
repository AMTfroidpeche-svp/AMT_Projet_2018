package ch.heig.gamification.entities;

/**
 * File : CompositeId.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : Embedded data used as an id to link object with the application token
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class CompositeId implements Serializable {

    private String apiToken = null;
    //name of the object
    private String name = null;

    public CompositeId(){}

    public CompositeId(String apiToken, String name){
        this.apiToken = apiToken;
        this.name = name;
    }

    public CompositeId(CompositeId c){
        this.apiToken = c.getApiToken();
        this.name = c.getName();
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
