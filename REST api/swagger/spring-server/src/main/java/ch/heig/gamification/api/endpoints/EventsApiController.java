package ch.heig.gamification.api.endpoints;

import io.avalia.gamification.api.EventsApi;
import io.avalia.gamification.api.model.Event;
import ch.heig.gamification.entities.EventEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class EventsApiController implements EventsApi {

    private EventEntity toEventEntity(Event Event) {
        EventEntity entity = new EventEntity();
        entity.setAppToken(Event.getApiToken());
        entity.setUserId(Event.getUserId());
        entity.setName(Event.getName());
        entity.setProperties(Event.getProperties());
        return entity;
    }

    private Event toEvent(EventEntity entity) {
        Event Event = new Event();
        Event.setApiToken(entity.getAppToken());
        Event.setUserId(entity.getUserId());
        Event.setName(entity.getName());
        Event.setProperties(entity.getProperties());
        return Event;
    }

    @Override
    public ResponseEntity<Object> generateEvent(Event event) {
        return null;
    }
}
