package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="BADGE")
public class BadgeEntity implements Serializable {

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

    public void setCompositeId(CompositeId compositeId) {
        this.compositeId = compositeId;
    }

    public BadgeEntity(){}

    public BadgeEntity(CompositeId id){
        this.compositeId = id;
    }

    public BadgeEntity(String apiToken, String badgeName){
        this(new CompositeId(apiToken, badgeName));
    }

    @Override
    public boolean equals(Object obj) {
        return compositeId.equals(((BadgeEntity) obj).getCompositeId());
    }

    @Override
    public int hashCode() {
        return compositeId.hashCode();
    }
}
