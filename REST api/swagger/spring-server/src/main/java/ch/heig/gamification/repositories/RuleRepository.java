package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepository extends CrudRepository<RuleEntity, Long>{

}
