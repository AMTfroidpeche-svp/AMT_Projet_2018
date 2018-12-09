package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.EventEntity;
import ch.heig.gamification.entities.EventPropertiesEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventPropertiesRepository extends CrudRepository<EventPropertiesEntity, Long>{

}
