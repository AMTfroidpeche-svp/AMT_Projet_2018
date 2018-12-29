package ch.heig.gamification.entities;

/**
 * File : LinkTableId.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : Embedded data used as id to link two tables with a many to many relation
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LinkTableId implements Serializable {

    private String apiToken = null;
    private String table1Id = null;
    private String table2Id = null;

    public LinkTableId(){}

    public LinkTableId(String apiToken, String table1Id, String table2Id){
        this.apiToken = apiToken;
        this.table1Id = table1Id;
        this.table2Id = table2Id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String gettable1Id() {
        return table1Id;
    }

    public String gettable2Id() {return table2Id;}

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void setTable1Id(String table1Id) {
        this.table1Id = table1Id;
    }

    public void setTable2Id(String table2Id) {
        this.table2Id = table2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkTableId)) return false;
        LinkTableId that = (LinkTableId) o;
        return Objects.equals(getApiToken(), that.getApiToken()) &&
                Objects.equals(gettable1Id(), that.gettable1Id()) &&
                Objects.equals(gettable2Id(), that.gettable2Id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApiToken(), gettable1Id(), gettable2Id());
    }

}
