package ch.heig.gamification.repositories;

/**
 * File : UserGenericEventCountRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.LinkTableId;
import ch.heig.gamification.entities.UserGenericEventCountEntity;
import ch.heig.gamification.entities.UserPointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserGenericEventCountRepository extends CrudRepository<UserGenericEventCountEntity, LinkTableId>{

}
