package ch.heig.gamification.entities;

/**
 * File : UserPointScaleEntity.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : UserPointScale entity store in the database. It contains the name of a pointScale and the amount of point
 * that a particular user got in this pointScale.
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="USERPOINTSCALE")
public class UserPointScaleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @AttributeOverrides({
            @AttributeOverride(name="id",column=@Column(name="pointScaleId")),
    })
    //table1ID is userName, table2ID is the pointScaleID
    private LinkTableId userPointScaleId;

    private int value;

    public UserPointScaleEntity() {
    }

    public UserPointScaleEntity(LinkTableId userPointScaleId){
        this.userPointScaleId = userPointScaleId;
    }

    public long getId() {
        return id;
    }

    public LinkTableId getUserPointScaleId() {
        return userPointScaleId;
    }

    public void setUserPointScaleId(LinkTableId userPointScaleId) {
        this.userPointScaleId = userPointScaleId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object obj) {
        return id == (((UserPointScaleEntity) obj).getId());
    }
}
