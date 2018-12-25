package ch.heig.gamification.entities;

import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="APPLICATION")
public class ApplicationEntity implements Serializable {

    @Id
    private String apiToken;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<BadgeEntity> badges = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PointScaleEntity> pointScales = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<RuleEntity> rules = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

    public ApplicationEntity(){}

    public ApplicationEntity(String apiToken){
        this.apiToken = apiToken;
    }

    public String getId() {
        return apiToken;
    }

    public void setId(String apiToken) {
        this.apiToken = apiToken;
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

    public List<RuleEntity> getRules() {
        return rules;
    }

    public void setRules(List<RuleEntity> rules) {
        this.rules = rules;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public void addBadge(BadgeEntity b){
        badges.add(b);
    }

    public void addPointScale(PointScaleEntity p){
        pointScales.add(p);
    }

    public void addRule(RuleEntity r){
        rules.add(r);
    }

    public void addUser(UserEntity u){
        users.add(u);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationEntity)) return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return Objects.equals(getId(), that.getId());
    }
}
