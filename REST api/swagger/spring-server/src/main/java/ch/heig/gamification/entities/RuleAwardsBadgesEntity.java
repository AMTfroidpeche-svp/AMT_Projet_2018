package ch.heig.gamification.entities;

/**
 * File : RuleAwardsBadgesEntity.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : RuleAwardsBadges entity store in the database. It contains all the badges to award when an event fulfilled
 * the rule conditions
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="RULEAWARDSBADGES")
public class RuleAwardsBadgesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @AttributeOverrides({
            @AttributeOverride(name="id",column=@Column(name="pointScaleId")),
    })
    //table1ID is ruleName, table2ID is the badgesID
    private LinkTableId ruleBadgesId;

    public RuleAwardsBadgesEntity() {
    }

    public RuleAwardsBadgesEntity(LinkTableId rulePointScaleId){
        this.ruleBadgesId = rulePointScaleId;
    }

    public long getId() {
        return id;
    }

    public LinkTableId getRuleBadgesId() {
        return ruleBadgesId;
    }

    public void setRuleBadgesId(LinkTableId ruleBadgesId) {
        this.ruleBadgesId = ruleBadgesId;
    }


    @Override
    public boolean equals(Object obj) {
        return id == (((RuleAwardsBadgesEntity) obj).getId());
    }
}
