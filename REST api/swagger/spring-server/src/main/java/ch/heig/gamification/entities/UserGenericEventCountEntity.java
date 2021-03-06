package ch.heig.gamification.entities;

/**
 * File : UserGenericEventCountEntity.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : UserGenericCountEvent entity store in the database. It contain the name of an event and the amount of time
 * that it has been triggered by a particular user
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="USERGENERICEVENTCOUNT")
public class UserGenericEventCountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //table1ID is userName, table2ID is the EventName
    private LinkTableId linkTableId;

    private int value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserGenericEventCountEntity() {
    }

    public UserGenericEventCountEntity(LinkTableId linkTableId){
        this.linkTableId = linkTableId;
    }

    public LinkTableId getLinkTableId() {
        return linkTableId;
    }

    public void setid(LinkTableId id) {
        this.linkTableId = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void incValue(){value++;}


    @Override
    public boolean equals(Object obj) {
        return linkTableId.equals(((UserGenericEventCountEntity) obj).getLinkTableId());
    }
}
