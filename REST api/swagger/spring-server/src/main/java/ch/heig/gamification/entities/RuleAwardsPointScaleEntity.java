package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="RULEAWARDSPOINTSCALE")
public class RuleAwardsPointScaleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
