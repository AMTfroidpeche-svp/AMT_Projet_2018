package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointScaleRepository extends CrudRepository<PointScaleEntity, CompositeId>{

    public PointScaleEntity findById(CompositeId id);

}
