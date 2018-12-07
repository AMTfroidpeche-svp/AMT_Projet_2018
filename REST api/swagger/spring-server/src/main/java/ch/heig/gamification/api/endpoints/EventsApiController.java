package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.EventPropertiesEntity;
import io.avalia.gamification.api.EventsApi;
import io.avalia.gamification.api.model.Event;
import ch.heig.gamification.entities.EventEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class EventsApiController implements EventsApi {

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
    public ResponseEntity<Object> generateEvent(Event event) {
        return null;
    }
}
