package ch.heig.gamification.repositories;

/**
 * File : RuleRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<RuleEntity, Long>{

}
