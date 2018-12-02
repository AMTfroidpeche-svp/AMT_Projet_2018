package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface BadgeRepository extends CrudRepository<BadgeEntity, String>{

}
