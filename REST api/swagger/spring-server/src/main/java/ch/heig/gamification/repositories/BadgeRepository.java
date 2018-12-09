package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.BadgeEntity;
import ch.heig.gamification.entities.CompositeId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeEntity, CompositeId>{

    public BadgeEntity findById(CompositeId id);

}
