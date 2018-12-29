package ch.heig.gamification.entities;

/**
 * File : EventPropertiesEntity.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : EventProperties entity store in the database. See details in the header of the event entity
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EVENTPROPERTIES")
public class EventPropertiesEntity implements Serializable {

    private String name;
    private int value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
