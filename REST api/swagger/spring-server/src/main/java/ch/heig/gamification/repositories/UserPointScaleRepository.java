package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserPointScaleRepository extends CrudRepository<UserPointScaleEntity, Long>{

    public UserPointScaleEntity findUserPointScaleEntitiesByUserPointScaleId(UserPointScaleId id);

}
