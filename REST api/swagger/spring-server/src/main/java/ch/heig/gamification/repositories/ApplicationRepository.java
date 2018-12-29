package ch.heig.gamification.repositories;

/**
 * File : ApplicationRepository.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 */

import ch.heig.gamification.entities.*;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, String>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    ApplicationEntity findByApiToken(String apiToken);
}
