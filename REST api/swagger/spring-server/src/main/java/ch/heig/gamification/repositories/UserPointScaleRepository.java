package ch.heig.gamification.repositories;

/**
 * File : UserPointScaleRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserPointScaleRepository extends CrudRepository<UserPointScaleEntity, Long>{

}
