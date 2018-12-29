package ch.heig.gamification.repositories;

/**
 * File : EventRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.EventEntity;
import ch.heig.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long>{

}
