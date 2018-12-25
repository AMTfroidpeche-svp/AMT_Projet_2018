package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RULEPROPERTIES")
public class RulePropertiesEntity implements Serializable {

    private String propertyName = null;

    private String type = null;

    private String compareOperator = null;

    private Integer value = null;

    @EmbeddedId
    private CompositeId id;

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(String compareOperator) {
        this.compareOperator = compareOperator;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(((RulePropertiesEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
