package ch.heig.gamification.api.endpoints;

/**
 * File : ApplicationsApiController.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This controller is used to drop the database, it is only necessary for the tests and must be deleted when
 * deploying the app
 */

import ch.heig.gamification.api.ApplicationsApi;
import ch.heig.gamification.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class ApplicationsApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;


    @Override
    public ResponseEntity<Void> dropDatabase() {
        applicationRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
