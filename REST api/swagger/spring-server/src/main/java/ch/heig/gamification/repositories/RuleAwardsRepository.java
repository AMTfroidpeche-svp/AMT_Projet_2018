package ch.heig.gamification.repositories;

/**
 * File : RuleAwardsRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.RuleAwardsEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleAwardsRepository extends CrudRepository<RuleAwardsEntity, Long>{

}
