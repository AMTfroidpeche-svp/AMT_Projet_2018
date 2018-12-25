package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.UserRepository;
import io.avalia.gamification.api.EventsApi;
import io.avalia.gamification.api.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    private EventEntity toEventEntity(Event Event) {
        EventEntity entity = new EventEntity();
        entity.setAppToken(Event.getApiToken());
        entity.setUserId(Event.getUserId());
        entity.setName(Event.getName());
        List<EventPropertiesEntity> eventProperties = new ArrayList<>();
        for (io.avalia.gamification.api.model.EventProperties ep: Event.getProperties()) {
            EventPropertiesEntity epe = new EventPropertiesEntity();
            epe.setName(ep.getName());
            epe.setValue(ep.getValue());
            eventProperties.add(epe);
        }
        entity.setProperties(eventProperties);
        return entity;
    }

    private Event toEvent(EventEntity entity) {
        Event Event = new Event();
        Event.setApiToken(entity.getAppToken());
        Event.setUserId(entity.getUserId());
        Event.setName(entity.getName());
        List<io.avalia.gamification.api.model.EventProperties> eventProperties = new ArrayList<>();
        for (EventPropertiesEntity epe: entity.getProperties()) {
            io.avalia.gamification.api.model.EventProperties ep = new io.avalia.gamification.api.model.EventProperties();
            ep.setName(epe.getName());
            ep.setValue(epe.getValue());
            eventProperties.add(ep);
        }
        Event.setProperties(eventProperties);
        return Event;
    }

    @Override
    public ResponseEntity<String> generateEvent(Event event) {
        EventEntity eventEntity = toEventEntity(event);
        //check if the app exist and contain at least one rule
        ApplicationEntity app = applicationRepository.findByApiToken(event.getApiToken());
        if(app == null || app.getRules().isEmpty()){
            return ResponseEntity.notFound().build();
        }

        //check if there is rule corresponding to the event
        List<RuleEntity> matchingRules = new ArrayList<>();
        for(RuleEntity r : app.getRules()){
            if(r.getEventName().equals(eventEntity.getName())){
                matchingRules.add(r);
            }
        }

        if(matchingRules.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        //check if the user concerned by the event exist
        UserEntity userConcerned = null;
        for(UserEntity u : app.getUsers()){
            if(u.getId().getName().equals(event.getUserId())){
                userConcerned = u;
                break;
            }
        }
        //if he doesn't exist, we create it
        if(userConcerned == null){
            userConcerned = new UserEntity(new CompositeId(event.getApiToken(), event.getUserId()));
            for(PointScaleEntity p : app.getPointScales()){
                userConcerned.addPointScale(p);
            }
        }
        StringBuilder elementAwarded = new StringBuilder();
        //for each rule
        for(RuleEntity r : matchingRules){
            //check if the properties are valid, if yes, we give the award to the user
            if(propertyOk(r.getProperties(), eventEntity.getProperties())){
                elementAwarded.append(addAwardsToUser(r.getAwards(), userConcerned));
            }
        }

        return ResponseEntity.ok(elementAwarded.toString());
    }

    private boolean propertyOk(List<RulePropertiesEntity> rulePropertiesEntities, List<EventPropertiesEntity> eventPropertiesEntities){
        return false;
    }

    private String addAwardsToUser(RuleAwardsEntity ruleAwardsEntity, UserEntity userEntity){
        return null;
    }
}
