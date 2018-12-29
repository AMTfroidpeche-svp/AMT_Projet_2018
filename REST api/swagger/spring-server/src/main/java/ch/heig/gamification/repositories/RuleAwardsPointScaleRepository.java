package ch.heig.gamification.repositories;

/**
 * File : RuleAwardsPointScaleRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.RuleAwardsPointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleAwardsPointScaleRepository extends CrudRepository<RuleAwardsPointScaleEntity, Long>{

}
