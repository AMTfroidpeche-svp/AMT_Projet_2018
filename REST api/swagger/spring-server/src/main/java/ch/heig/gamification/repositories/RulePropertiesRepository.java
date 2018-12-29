package ch.heig.gamification.repositories;

/**
 * File : RulePropertiesRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.RulePropertiesEntity;
import org.springframework.data.repository.CrudRepository;

public interface RulePropertiesRepository extends CrudRepository<RulePropertiesEntity, Long>{

}
