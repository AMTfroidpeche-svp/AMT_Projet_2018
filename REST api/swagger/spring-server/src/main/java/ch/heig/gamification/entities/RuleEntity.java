package ch.heig.gamification.entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import io.avalia.gamification.api.model.RuleAwards;
import io.avalia.gamification.api.model.RuleProperties;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class RuleEntity implements Serializable {

    private String apiToken;
    private String name;
    private RuleAwards awards;
    private List<RuleProperties> propreties;

    public String getId() {
        return apiToken+name;
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
