package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.RuleAwardsEntity;
import ch.heig.gamification.entities.RulePropertiesEntity;
import org.springframework.data.repository.CrudRepository;

public interface RulePropertiesRepository extends CrudRepository<RulePropertiesEntity, CompositeId>{

}
