package ch.heig.gamification.entities;

/**
 * File : PointScaleEntity.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : PointScale entity store in the database
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="POINTSCALE")
public class PointScaleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private CompositeId compositeId;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId id) {
        this.compositeId = id;
    }

    public PointScaleEntity(){}

    public PointScaleEntity(CompositeId compositeId){
        this.compositeId = compositeId;
    }

    public PointScaleEntity(String apiToken, String pointScaleName){
        this(new CompositeId(apiToken, pointScaleName));
    }

    @Override
    public boolean equals(Object obj) {
        return compositeId.equals(((PointScaleEntity) obj).getCompositeId());
    }

    @Override
    public int hashCode() {
        return compositeId.hashCode();
    }

}
