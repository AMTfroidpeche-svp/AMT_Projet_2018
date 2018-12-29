package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.api.ApplicationsApi;
import ch.heig.gamification.api.BadgesApi;
import ch.heig.gamification.api.model.Badge;
import ch.heig.gamification.api.model.UpdateBadge;
import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.ApplicationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
