package ch.heig.gamification.repositories;

/**
 * File : RuleAwardsBadgesRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.RuleAwardsBadgesEntity;
import org.springframework.data.repository.CrudRepository;

public interface RuleAwardsBadgesRepository extends CrudRepository<RuleAwardsBadgesEntity, Long>{

}
