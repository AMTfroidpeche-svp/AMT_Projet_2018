package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

public interface BadgeRepository extends CrudRepository<BadgeEntity, String>{

}
