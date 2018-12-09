package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERPOINTSCALE")
public class UserPointScaleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @AttributeOverrides({
            @AttributeOverride(name="id",column=@Column(name="pointScaleId")),
    })
    private UserPointScaleId userPointScaleId;

    private int value;

    public UserPointScaleEntity() {
    }

    public UserPointScaleEntity(UserPointScaleId userPointScaleId){
        this.userPointScaleId = userPointScaleId;
    }

    public long getId() {
        return id;
    }

    public UserPointScaleId getUserPointScaleId() {
        return userPointScaleId;
    }

    public void setUserPointScaleId(UserPointScaleId userPointScaleId) {
        this.userPointScaleId = userPointScaleId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
