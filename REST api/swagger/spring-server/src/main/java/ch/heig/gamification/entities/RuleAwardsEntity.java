package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RULEAWARDS")
public class RuleAwardsEntity implements Serializable {


    @OneToMany(cascade = {CascadeType.ALL})
    @AttributeOverrides({
            @AttributeOverride(name="id",column=@Column(name="ruleId")),
    })
    //table1ID is userName, table2ID is the badgeID
    private List<RuleAwardsBadgesEntity> ruleAwardsBadgesId;


    @OneToMany(cascade = {CascadeType.ALL})
    @AttributeOverrides({
            @AttributeOverride(name="id",column=@Column(name="pointScaleId")),
    })
    //table1ID is userName, table2ID is the pointScaleID
    private List<RuleAwardsPointScaleEntity> ruleAwardsPointScaleId;

    private String amountofPoint;

    @EmbeddedId
    private CompositeId id;

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
    }

    public void setRuleAwardsBadgesId(List<RuleAwardsBadgesEntity> ruleAwardsBadgesId) {
        this.ruleAwardsBadgesId = ruleAwardsBadgesId;
    }

    public List<RuleAwardsBadgesEntity> getRuleAwardsBadgesId() {
        return ruleAwardsBadgesId;
    }

    public void setruleAwardsPointScaleId(List<RuleAwardsPointScaleEntity> ruleAwardsPointScaleId) {
        this.ruleAwardsPointScaleId = ruleAwardsPointScaleId;
    }

    public List<RuleAwardsPointScaleEntity> getruleAwardsPointScaleId() {
        return ruleAwardsPointScaleId;
    }

    public List<Integer> getAmountofPoint() {
        String[] stringInteger = amountofPoint.split(",");
        List<Integer> ret = new ArrayList<>();
        for(int i = 0; i < stringInteger.length; i++){
            ret.add(Integer.parseInt(stringInteger[i]));
        }
        return ret;
    }

    public void setAmountofPoint(List<Integer> amountofPoint) {
        StringBuilder sb = new StringBuilder();
        for (int i : amountofPoint) {
            sb.append(i).append(",");
        }
        this.amountofPoint = sb.toString();
    }

    @Override
    public boolean equals(Object obj) {return id.equals(((RuleAwardsEntity) obj).getId());}

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
