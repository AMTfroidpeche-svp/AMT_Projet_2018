package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
public class UserEntity implements Serializable {

    @EmbeddedId
    private CompositeId id;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<BadgeEntity> badges = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<PointScaleEntity> pointScales = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private List<UserPointScaleEntity> userPointScaleEntities = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private List<UserGenericEventCountEntity> userGenericEventCountEntities = new ArrayList<>();

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

    public List<UserGenericEventCountEntity> getUserGenericEventCountEntities() {
        return userGenericEventCountEntities;
    }

    public void setUserGenericEventCountEntities(List<UserGenericEventCountEntity> userGenericEventCountEntities) {
        this.userGenericEventCountEntities = userGenericEventCountEntities;
    }

    public void addBadge(BadgeEntity b){
        this.badges.add(b);
    }

    public void addPointScale(PointScaleEntity p){
        this.pointScales.add(p);
        this.userPointScaleEntities.add(new UserPointScaleEntity(new LinkTableId(id.getApiToken(), id.getName(), p.getCompositeId().getName())));
    }

    public void addEventCount(UserGenericEventCountEntity event){
        this.userGenericEventCountEntities.add(event);
    }

    public void modifyPoint(PointScaleEntity p, int amount){
        for(int i = 0; i < userPointScaleEntities.size(); i++){
            if(userPointScaleEntities.get(i).getUserPointScaleId().getApiToken().equals(p.getCompositeId().getApiToken()) &&
                    userPointScaleEntities.get(i).getUserPointScaleId().gettable1Id().equals(p.getCompositeId().getName())){
                userPointScaleEntities.get(i).setValue(userPointScaleEntities.get(i).getValue() + amount);
                return;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(((UserEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
