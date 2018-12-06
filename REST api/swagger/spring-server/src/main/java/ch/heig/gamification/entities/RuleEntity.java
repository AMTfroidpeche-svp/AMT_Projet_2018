package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.avalia.gamification.api.model.RuleAwards;
import io.avalia.gamification.api.model.RuleProperties;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
@Table(name="RULE")
public class RuleEntity implements Serializable {

    @Column(name="APITOKEN")
    private String apiToken;
    @Column(name="NAME")
    private String name;
    @OneToOne(cascade=CascadeType.ALL, targetEntity=RuleAwards.class)
    @JoinColumn(name="ID")
    private RuleAwards awards = new RuleAwards();
    @OneToOne(cascade=CascadeType.ALL, targetEntity=RuleProperties.class)
    @JoinColumn(name="ID")
    private List<RuleProperties> propreties = new ArrayList<>();

    @Id
    @Column(name="ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setApiToken(String apiToken) {
        this.id = apiToken + name;
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setName(String name) {
        this.id = apiToken + name;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAwards(RuleAwards awards) {
        this.awards = awards;
    }

    public RuleAwards getAwards() {
        return awards;
    }

    public void setProperties(List<RuleProperties> propreties) {
        this.propreties = propreties;
    }

    public List<RuleProperties> getProperties() {
        return propreties;
    }

}
