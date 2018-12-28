package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.RuleAwardsEntity;
import ch.heig.gamification.entities.RuleAwardsPointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleAwardsPointScaleRepository extends CrudRepository<RuleAwardsPointScaleEntity, Long>{

}
