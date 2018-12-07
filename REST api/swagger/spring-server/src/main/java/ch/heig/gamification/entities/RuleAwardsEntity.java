package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RuleAwardsEntity implements Serializable {

    @OneToMany
    private List<BadgeEntity> badge = new ArrayList<BadgeEntity>();

    @OneToMany
    private List<RuleAwardsPointEntity> point = new ArrayList<RuleAwardsPointEntity>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public List<BadgeEntity> getBadge() {
        return badge;
    }

    public void setBadge(List<BadgeEntity> badge) {
        this.badge = badge;
    }

    public List<RuleAwardsPointEntity> getPoint() {
        return point;
    }

    public void setPoint(List<RuleAwardsPointEntity> point) {
        this.point = point;
    }
}
