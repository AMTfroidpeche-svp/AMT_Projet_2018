package ch.heig.gamification.repositories;

import ch.heig.gamification.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, String>{

    public ApplicationEntity findByApiToken(String apiToken);

    public List<PointScaleEntity> getPointScalesByApiToken(String apiToken);

    public List<UserEntity> getUsersByApiToken(String apiToken);

    public List<BadgeEntity> getApplicationEntitiesByApiToken(String apiToken);

}
