package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="POINTSCALE")
public class PointScaleEntity implements Serializable {

    @EmbeddedId
    private CompositeId id;

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
    }

    public PointScaleEntity(){}

    public PointScaleEntity(CompositeId id){
        this.id = id;
    }

}
