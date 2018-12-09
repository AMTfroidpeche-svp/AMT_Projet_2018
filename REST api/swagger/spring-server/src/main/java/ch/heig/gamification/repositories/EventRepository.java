package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.EventEntity;
import ch.heig.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long>{

    public EventEntity findByNameAndAndApiTokenAndUserId(String name, String apiToken, String userId);
}
