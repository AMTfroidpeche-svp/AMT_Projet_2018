package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.UserGenericEventCountRepository;
import ch.heig.gamification.repositories.UserRepository;
import io.avalia.gamification.api.EventsApi;
import io.avalia.gamification.api.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGenericEventCountRepository userGenericEventCountEntity;

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
            app.addUser(userConcerned);
        }
        StringBuilder elementAwarded = new StringBuilder();
        //for each rule
        for(RuleEntity r : matchingRules){
            //adding the event to the event count of the user
            addToEventCount(r.getEventName(), userConcerned);
            //check if the properties are valid, if yes, we give the award to the user
            try {
                if(propertyOk(r.getProperties(), eventEntity.getProperties(), userConcerned)){
                    elementAwarded.append(addAwardsToUser(r.getAwards(), userConcerned));
                }
                applicationRepository.save(app);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(elementAwarded.toString());
    }

    private boolean propertyOk(List<RulePropertiesEntity> rulePropertiesEntities, List<EventPropertiesEntity> eventPropertiesEntities, UserEntity userConcerned) throws ScriptException {
        //for each property in the rule, we find the corresponding property in the event
        for(RulePropertiesEntity rulePropertiesEntity : rulePropertiesEntities){
            boolean validated = false;
            for(EventPropertiesEntity eventPropertiesEntity : eventPropertiesEntities){
                if(rulePropertiesEntity.getPropertyName().equals(eventPropertiesEntity.getName())){
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    if(rulePropertiesEntity.getType().equals("amount")){
                        LinkTableId eventId = new LinkTableId(userConcerned.getId().getApiToken(), userConcerned.getId().getName(), eventPropertiesEntity.getName());
                        UserGenericEventCountEntity eventCount = userGenericEventCountEntity.findById(eventId);
                        int value = eventCount.getValue();
                        String expression = String.valueOf(value) + rulePropertiesEntity.getCompareOperator() + rulePropertiesEntity.getValue();
                        if(!((boolean)engine.eval(expression))){
                            return false;
                        }
                        else{
                            validated = true;
                            break;
                        }
                    }
                    else if(rulePropertiesEntity.getType().equals("value")){
                        String expression = eventPropertiesEntity.getValue() + rulePropertiesEntity.getCompareOperator() + rulePropertiesEntity.getValue();
                        if(!((boolean)engine.eval(expression))){
                            return false;
                        }
                        else{
                            validated = true;
                            break;
                        }
                    }
                    //this should never happen as we are suppose to control at the creation of a rule that the type is either value or amount
                    else{
                        throw new RuntimeException("This rule can't exist");
                    }
                }
            }
            if(!validated){
                return false;
            }
        }
        return true;
    }

    private String addAwardsToUser(RuleAwardsEntity ruleAwardsEntity, UserEntity userEntity){
        //adding each badges
        for(RuleAwardsBadgesEntity b : ruleAwardsEntity.getRuleAwardsBadgesId()){
            BadgeEntity newBadge = new BadgeEntity(b.getRuleBadgesId().getApiToken(), b.getRuleBadgesId().gettable2Id());
            if(!userEntity.getBadges().contains(newBadge)){
                userEntity.addBadge(newBadge);
            }
        }
        //update all pointScales
        List<RuleAwardsPointScaleEntity> pointScaleEntities = ruleAwardsEntity.getruleAwardsPointScaleId();
        for(int i = 0; i < pointScaleEntities.size(); i++){
            for(int j = 0; j < userEntity.getUserPointScaleEntities().size(); j++){
                if(pointScaleEntities.get(i).getRulePointScaleId().gettable2Id().equals(userEntity.getUserPointScaleEntities().get(j).getUserPointScaleId().gettable2Id())){
                    int previousValue = userEntity.getUserPointScaleEntities().get(j).getValue();
                    userEntity.getUserPointScaleEntities().get(j).setValue(previousValue + ruleAwardsEntity.getAmountofPoint().get(i));
                    break;
                }
            }
        }
        return null;
    }

    private void addToEventCount(String eventName, UserEntity userEntity){
        for(UserGenericEventCountEntity event : userEntity.getUserGenericEventCountEntities()){
            if(event.getId().gettable2Id().equals(eventName)){
                event.incValue();
                return;
            }
        }
        UserGenericEventCountEntity newEvent = new UserGenericEventCountEntity();
        newEvent.setid(new LinkTableId(userEntity.getId().getApiToken(), userEntity.getId().getName(), eventName));
        newEvent.setValue(1);
        userEntity.addEventCount(newEvent);
    }
}
