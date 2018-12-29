package ch.heig.gamification.repositories;

/**
 * File : UserRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, CompositeId>{

    UserEntity findById(CompositeId Id);

    List<UserEntity> findByIdApiToken(String apiToken);

}
