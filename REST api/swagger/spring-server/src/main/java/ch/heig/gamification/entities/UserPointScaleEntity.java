package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
