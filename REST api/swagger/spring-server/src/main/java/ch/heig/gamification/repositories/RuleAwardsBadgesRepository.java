package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.RuleAwardsBadgesEntity;
import ch.heig.gamification.entities.RuleAwardsEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleAwardsBadgesRepository extends CrudRepository<RuleAwardsBadgesEntity, Long>{

}
