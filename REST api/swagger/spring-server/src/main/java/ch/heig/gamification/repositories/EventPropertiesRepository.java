package ch.heig.gamification.repositories;

/**
 * File : EventPropertiesRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.EventPropertiesEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventPropertiesRepository extends CrudRepository<EventPropertiesEntity, Long>{

}
