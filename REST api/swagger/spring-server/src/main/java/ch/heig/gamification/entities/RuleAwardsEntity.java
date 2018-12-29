package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RULEAWARDS")
public class RuleAwardsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @OneToMany(cascade = {CascadeType.ALL})
    @AttributeOverrides({
            @AttributeOverride(name="compositeId",column=@Column(name="ruleId")),
    })
    //table1ID is userName, table2ID is the badgeID
    private List<RuleAwardsBadgesEntity> ruleAwardsBadgesId;


    @OneToMany(cascade = {CascadeType.ALL})
    @AttributeOverrides({
            @AttributeOverride(name="compositeId",column=@Column(name="pointScaleId")),
    })
    //table1ID is userName, table2ID is the pointScaleID
    private List<RuleAwardsPointScaleEntity> ruleAwardsPointScaleId;

    private String amountofPoint;

    private CompositeId compositeId;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId id) {
        this.compositeId = id;
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
        if(stringInteger[0].equals("")){
            return null;
        }
        List<Integer> ret = new ArrayList<>();
        for(int i = 0; i < stringInteger.length; i++){
            ret.add(Integer.parseInt(stringInteger[i]));
        }
        return ret;
    }

    public void setAmountofPoint(List<Integer> amountofPoint) {
        if(amountofPoint == null){
            this.amountofPoint = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : amountofPoint) {
            sb.append(i).append(",");
        }
        this.amountofPoint = sb.toString();
    }

    @Override
    public boolean equals(Object obj) {return compositeId.equals(((RuleAwardsEntity) obj).getCompositeId());}

    @Override
    public int hashCode() {
        return compositeId.hashCode();
    }

}
