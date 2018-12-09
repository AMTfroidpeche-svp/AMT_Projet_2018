package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.entities.BadgeEntity;
import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.BadgeRepository;
import ch.heig.gamification.repositories.UserRepository;
import io.avalia.gamification.api.BadgesApi;
import io.avalia.gamification.api.model.Badge;
import io.avalia.gamification.api.model.AppInfos;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    public ResponseEntity<Object> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        ApplicationEntity app = applicationRepository.findByApiToken(badge.getApiToken());
        if(app == null){
            app = new ApplicationEntity(badge.getApiToken());
        }
        else{
            List<BadgeEntity> badges = app.getBadges();
            for (int i = 0; i < badges.size(); i++){
                if(badges.get(i).getId().equals(newBadgeEntity.getId())){
                    return ResponseEntity.status(304).build();
                }
            }
        }
        app.addBadge(newBadgeEntity);
        badgeRepository.save(newBadgeEntity);
        applicationRepository.save(app);
        CompositeId id = newBadgeEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId().getApiToken() + newBadgeEntity.getId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Badge>> getBadges(AppInfos infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos.getApiToken());
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        List<BadgeEntity> badgeEntities = app.getBadges();
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeEntities) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    private BadgeEntity toBadgeEntity(Badge badge) {
        BadgeEntity entity = new BadgeEntity(new CompositeId(badge.getApiToken(), badge.getName()));
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setApiToken(entity.getId().getApiToken());
        badge.setName(entity.getId().getName());
        return badge;
    }

}
