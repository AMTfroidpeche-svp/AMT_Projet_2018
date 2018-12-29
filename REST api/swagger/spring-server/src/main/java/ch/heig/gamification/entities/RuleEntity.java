package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="RULE")
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private CompositeId compositeId;

    private String eventName;

    @OneToOne(cascade = {CascadeType.ALL})
    private RuleAwardsEntity awards;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<RulePropertiesEntity> propreties = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RuleEntity(){}

    public RuleEntity(CompositeId compositeId){
        this.compositeId = compositeId;
    }

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId id) {
        this.compositeId = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setAwards(RuleAwardsEntity awards) {
        this.awards = awards;
    }

    public RuleAwardsEntity getAwards() {
        return awards;
    }

    public void setProperties(List<RulePropertiesEntity> propreties) {
        this.propreties = propreties;
    }

    public List<RulePropertiesEntity> getProperties() {
        return propreties;
    }

    @Override
    public boolean equals(Object obj) {
        return compositeId.equals(((RuleEntity) obj).getCompositeId());
    }

    @Override
    public int hashCode() {
        return compositeId.hashCode();
    }

}
