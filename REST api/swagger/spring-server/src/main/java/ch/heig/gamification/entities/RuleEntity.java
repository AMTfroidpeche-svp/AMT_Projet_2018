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

    @Column(name="APITOKEN")
    private String apiToken;

    @Column(name="NAME")
    private String name;

    @OneToOne
    private RuleAwardsEntity awards;

    @OneToMany
    private List<RulePropertiesEntity> propreties = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
