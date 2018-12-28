package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.api.BadgesApi;
import ch.heig.gamification.api.model.Badge;
import ch.heig.gamification.api.model.AppInfos;
import ch.heig.gamification.api.model.UpdateBadge;
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
public class BadgesApiController implements BadgesApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Transactional
    public ResponseEntity<Object> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        ApplicationEntity app = applicationRepository.findByApiToken(badge.getApiToken());
        if (app == null) {
            app = new ApplicationEntity(badge.getApiToken());
        } else {
            List<BadgeEntity> badges = app.getBadges();
            for (int i = 0; i < badges.size(); i++) {
                if (badges.get(i).getId().equals(newBadgeEntity.getId())) {
                    return ResponseEntity.status(304).build();
                }
            }
        }
        app.addBadge(newBadgeEntity);
        ApplicationEntity savedApp = applicationRepository.save(app);
        CompositeId id = newBadgeEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId().getApiToken() + newBadgeEntity.getId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @Transactional
    public ResponseEntity<Badge> deleteBadge(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Badge badge) {
        BadgeEntity badgeEntity = toBadgeEntity(badge);
        ApplicationEntity app = applicationRepository.findByApiToken(badgeEntity.getId().getApiToken());
        if (app == null || app.getBadges().indexOf(badgeEntity) == -1) {
            return ResponseEntity.notFound().build();
        } else {
            //for each user remove the badge
            for(int i = 0; i < app.getUsers().size(); i++){
                UserEntity u = app.getUsers().get(i);
                int index;
                if((index = u.getBadges().indexOf(badgeEntity)) != -1){
                    u.getBadges().remove(index);
                    i--;
                }
            }
            //for each rules remove the badge
            int indexBadge = 0;
            for(int i = 0; i < app.getRules().size(); i++){
                RuleEntity r = app.getRules().get(i);
                LinkTableId linkTableId = new LinkTableId(r.getId().getApiToken(), r.getId().getName(), badgeEntity.getId().getName());
                for(int j = 0; j < r.getAwards().getRuleAwardsBadgesId().size(); j++) {
                    if (r.getAwards().getRuleAwardsBadgesId().get(j).getRuleBadgesId().equals(linkTableId)) {
                        r.getAwards().getRuleAwardsBadgesId().remove(indexBadge);
                    }
                }
                //if the rule become empty, we removed it
                if (r.getAwards().getRuleAwardsBadgesId().size() == 0 && r.getAwards().getruleAwardsPointScaleId().size() == 0) {
                    for(UserEntity userEntity : app.getUsers()){
                        for(int k = 0; k < userEntity.getUserGenericEventCountEntities().size(); k++){
                            if(userEntity.getUserGenericEventCountEntities().get(k).getId().gettable2Id().equals(app.getRules().get(i).getEventName())){
                                userEntity.getUserGenericEventCountEntities().remove(k);
                            }
                        }
                    }
                    app.getRules().remove(i);
                    i--;
                }
            }
            //remove the badge from the app
            for(int i = 0; i < app.getBadges().size(); i++){
                if((indexBadge = app.getBadges().indexOf(badgeEntity)) != -1){
                    app.getBadges().remove(indexBadge);
                    i--;
                }
            }
            applicationRepository.save(app);
            return ResponseEntity.ok(badge);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Badge>> getBadges(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "apiToken", required = true)  String infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos);
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        List<BadgeEntity> badgeEntities = app.getBadges();
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeEntities) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    @Transactional
    public ResponseEntity<Badge> updateBadge(@ApiParam(value = "" ,required=true )  @Valid @RequestBody UpdateBadge updatebadge) {
        BadgeEntity oldBadge = new BadgeEntity();
        oldBadge.setId(new CompositeId(updatebadge.getNewBadge().getApiToken(), updatebadge.getOldName()));
        BadgeEntity newBadge = toBadgeEntity(updatebadge.getNewBadge());
        ApplicationEntity app = applicationRepository.findByApiToken(newBadge.getId().getApiToken());
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        else{
            int index;
            if((index = app.getBadges().indexOf(oldBadge)) != -1){
                app.getBadges().set(index, newBadge);
                for(RuleEntity r : app.getRules()){
                    for(RuleAwardsBadgesEntity b : r.getAwards().getRuleAwardsBadgesId()){
                        if(b.getRuleBadgesId().gettable2Id().equals(oldBadge.getId().getName())) {
                            b.setRuleBadgesId(new LinkTableId(b.getRuleBadgesId().getApiToken(), b.getRuleBadgesId().gettable1Id(), newBadge.getId().getName()));
                        }
                    }
                }

                for (UserEntity u : app.getUsers()){
                    for (BadgeEntity b : u.getBadges()){
                        if(b.getId().getName().equals(oldBadge.getId().getName())){
                            b.setId(newBadge.getId());
                        }
                    }
                }
                applicationRepository.save(app);
                return ResponseEntity.ok(toBadge(newBadge));
            }
            return ResponseEntity.notFound().build();
        }
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
