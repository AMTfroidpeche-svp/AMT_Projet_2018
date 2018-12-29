package ch.heig.gamification.entities;

/**
 * File : RuleAwardsPointScaleEntity.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : RuleAwardsPointScales entity store in the database. It contains all the pointScales to award when an event fulfilled
 * the rule conditions
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="RULEAWARDSPOINTSCALE")
public class RuleAwardsPointScaleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @AttributeOverrides({
            @AttributeOverride(name="id",column=@Column(name="pointScaleId")),
    })
    //table1ID is userName, table2ID is the pointScaleID
    private LinkTableId rulePointScaleId;

    public RuleAwardsPointScaleEntity() {
    }

    public RuleAwardsPointScaleEntity(LinkTableId rulePointScaleId){
        this.rulePointScaleId = rulePointScaleId;
    }

    public long getId() {
        return id;
    }

    public LinkTableId getRulePointScaleId() {
        return rulePointScaleId;
    }

    public void setRulePointScaleId(LinkTableId rulePointScaleId) {
        this.rulePointScaleId = rulePointScaleId;
    }


    @Override
    public boolean equals(Object obj) {
        return id == (((RuleAwardsPointScaleEntity) obj).getId());
    }
}
