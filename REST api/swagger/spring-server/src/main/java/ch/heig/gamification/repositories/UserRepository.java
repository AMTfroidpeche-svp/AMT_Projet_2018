package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.PointScaleEntity;
import ch.heig.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String>{

}
