package ch.heig.gamification.entities;

import io.avalia.gamification.api.model.RuleAwards;
import io.avalia.gamification.api.model.RuleProperties;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
public class UserEntity implements Serializable {

    @EmbeddedId
    private CompositeId id;

    @ManyToMany
    private List<BadgeEntity> badges = new ArrayList<>();

    @ManyToMany
    private List<PointScaleEntity> pointScales = new ArrayList<>();

    @OneToMany
    private List<UserPointScaleEntity> userPointScaleEntities = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(CompositeId id){
        this.id = id;
    }

    public CompositeId getId() {
        return id;
    }

    public void setId(CompositeId id) {
        this.id = id;
    }

    public List<BadgeEntity> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeEntity> badges) {
        this.badges = badges;
    }

    public List<PointScaleEntity> getPointScales() {
        return pointScales;
    }

    public void setPointScales(List<PointScaleEntity> pointScales) {
        this.pointScales = pointScales;
    }

    public List<UserPointScaleEntity> getUserPointScaleEntities() {
        return userPointScaleEntities;
    }

    public void setUserPointScaleEntities(List<UserPointScaleEntity> userPointScaleEntities) {
        this.userPointScaleEntities = userPointScaleEntities;
    }

    public void addBadge(BadgeEntity b){
        this.badges.add(b);
    }

    public void addPointScale(PointScaleEntity p){
        this.pointScales.add(p);
    }

    public void modifyPoint(PointScaleEntity p, int amount){
        for(int i = 0; i < userPointScaleEntities.size(); i++){
            if(userPointScaleEntities.get(i).getUserPointScaleId().getApiToken().equals(p.getId().getApiToken()) &&
                    userPointScaleEntities.get(i).getUserPointScaleId().getPointScaleName().equals(p.getId().getName())){
                userPointScaleEntities.get(i).setValue(userPointScaleEntities.get(i).getValue() + amount);
                return;
            }
        }
    }

}
