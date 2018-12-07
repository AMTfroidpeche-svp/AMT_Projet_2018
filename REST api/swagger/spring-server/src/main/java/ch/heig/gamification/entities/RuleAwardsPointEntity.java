package ch.heig.gamification.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class RuleAwardsPointEntity implements Serializable {

    private PointScaleEntity pointScaleEntity;
    private int value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public PointScaleEntity getPointScaleEntity() {
        return pointScaleEntity;
    }

    public void setPointScaleEntity(PointScaleEntity pointScaleEntity) {
        this.pointScaleEntity = pointScaleEntity;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
