package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="BADGE")
public class BadgeEntity implements Serializable {

    @EmbeddedId
    private CompositeId id;

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
    }

    public BadgeEntity(){}

    public BadgeEntity(CompositeId id){
        this.id = id;
    }

}
