package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.LinkTableId;
import ch.heig.gamification.entities.UserGenericEventCountEntity;
import ch.heig.gamification.entities.UserPointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserGenericEventCountRepository extends CrudRepository<UserGenericEventCountEntity, LinkTableId>{

}
