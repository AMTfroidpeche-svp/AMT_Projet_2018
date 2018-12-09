package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RULEAWARDS")
public class RuleAwardsEntity implements Serializable {

    @OneToMany
    private List<BadgeEntity> badge = new ArrayList<BadgeEntity>();

    @OneToMany
    private List<PointScaleEntity> point = new ArrayList<>();

    private String amountofPoint;

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

    public List<PointScaleEntity> getPoint() {
        return point;
    }

    public void setPoint(List<PointScaleEntity> point) {
        this.point = point;
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
}
