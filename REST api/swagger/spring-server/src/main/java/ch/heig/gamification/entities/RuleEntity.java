package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.avalia.gamification.api.model.RuleAwards;
import io.avalia.gamification.api.model.RuleProperties;

@Entity
@Table(name="RULE")
public class RuleEntity implements Serializable {

    @EmbeddedId
    private CompositeId id;

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
    }

    @OneToOne
    private RuleAwardsEntity awards;

    @OneToMany
    private List<RulePropertiesEntity> propreties = new ArrayList<>();

    public RuleEntity(){}

    public RuleEntity(CompositeId id){
        this.id = id;
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

}
