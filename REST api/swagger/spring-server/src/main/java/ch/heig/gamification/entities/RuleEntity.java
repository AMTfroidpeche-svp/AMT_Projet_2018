package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.avalia.gamification.api.model.RuleAwards;
import io.avalia.gamification.api.model.RuleProperties;

@Entity
@Table(name="RULE")
public class RuleEntity implements Serializable {

    @EmbeddedId
    private CompositeId id;

    private String eventName;

    @OneToOne(cascade = {CascadeType.ALL})
    private RuleAwardsEntity awards;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<RulePropertiesEntity> propreties = new ArrayList<>();

    public RuleEntity(){}

    public RuleEntity(CompositeId id){
        this.id = id;
    }

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
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
        return id.equals(((RuleEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
