package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RULEPROPERTIES")
public class RulePropertiesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String propertyName = null;

    private String type = null;

    private String compareOperator = null;

    private Integer value = null;

    private CompositeId compositeId;

    public long getId() {
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
        return compositeId.equals(((RulePropertiesEntity) obj).getCompositeId());
    }

    @Override
    public int hashCode() {
        return compositeId.hashCode();
    }


}
