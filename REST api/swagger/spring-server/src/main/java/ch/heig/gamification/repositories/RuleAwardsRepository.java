package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.RuleAwardsEntity;
import ch.heig.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleAwardsRepository extends CrudRepository<RuleAwardsEntity, Long>{

}
