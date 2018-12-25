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

    public PointScaleEntity(String apiToken, String pointScaleName){
        this(new CompositeId(apiToken, pointScaleName));
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(((PointScaleEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
