package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RulePropertiesEntity implements Serializable {

    private String type = null;

    private String compareOperator = null;

    private Integer value = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
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
}
