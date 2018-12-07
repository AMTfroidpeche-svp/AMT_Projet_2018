package ch.heig.gamification.entities;

import io.avalia.gamification.api.model.RuleAwards;
import io.avalia.gamification.api.model.RuleProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="RULE")
public class UserEntity implements Serializable {

    @Column(name="USERID")
    private String userId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
