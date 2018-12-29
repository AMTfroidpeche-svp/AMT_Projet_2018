package ch.heig.gamification.repositories;

/**
 * File : PointScaleRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface PointScaleRepository extends CrudRepository<PointScaleEntity, Long>{

}
