package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.BadgeEntity;
import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.PointScaleEntity;
import ch.heig.gamification.entities.UserEntity;
import org.h2.engine.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, CompositeId>{

    public UserEntity findById(CompositeId Id);

    public List<UserEntity> findByIdApiToken(String apiToken);

}
