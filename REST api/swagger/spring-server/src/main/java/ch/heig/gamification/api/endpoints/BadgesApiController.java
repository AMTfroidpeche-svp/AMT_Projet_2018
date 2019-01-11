package ch.heig.gamification.api.endpoints;

/**
 * File : BadgesApiController.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 11.01.2019
 *
 * Description : This controller is used to operate CRUD operations on badges
 */

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.api.BadgesApi;
import ch.heig.gamification.api.model.Badge;
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
    public synchronized ResponseEntity<Object> createBadge(@NotNull @ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        ApplicationEntity app = applicationRepository.findByApiToken(badge.getApiToken());
        //check if the app exist, if not we creted it
        if (app == null) {
            app = new ApplicationEntity(badge.getApiToken());
        } else {
            List<BadgeEntity> badges = app.getBadges();
            //check if the badge already exists, if yes, return a 304 response
            for (int i = 0; i < badges.size(); i++) {
                if (badges.get(i).getCompositeId().equals(newBadgeEntity.getCompositeId())) {
                    return ResponseEntity.status(304).build();
                }
            }
        }
        app.addBadge(newBadgeEntity);
        ApplicationEntity savedApp = applicationRepository.save(app);

        CompositeId id = newBadgeEntity.getCompositeId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getCompositeId().getApiToken() + newBadgeEntity.getCompositeId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @Transactional
    public synchronized ResponseEntity<Badge> deleteBadge(@NotNull @ApiParam(value = "" ,required=true )  @Valid @RequestBody Badge badge) {
        BadgeEntity badgeEntity = toBadgeEntity(badge);
        ApplicationEntity app = applicationRepository.findByApiToken(badgeEntity.getCompositeId().getApiToken());
        //check if the app and if the badge exist in the app, if not, send a 404 error
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
                LinkTableId linkTableId = new LinkTableId(r.getCompositeId().getApiToken(), r.getCompositeId().getName(), badgeEntity.getCompositeId().getName());
                for(int j = 0; j < r.getAwards().getRuleAwardsBadgesId().size(); j++) {
                    if (r.getAwards().getRuleAwardsBadgesId().get(j).getRuleBadgesId().equals(linkTableId)) {
                        r.getAwards().getRuleAwardsBadgesId().remove(indexBadge);
                    }
                }
                //if the rule become empty, we removed it
                if (r.getAwards().getRuleAwardsBadgesId().size() == 0 && r.getAwards().getruleAwardsPointScaleId().size() == 0) {
                    for(UserEntity userEntity : app.getUsers()){
                        for(int k = 0; k < userEntity.getUserGenericEventCountEntities().size(); k++){
                            if(userEntity.getUserGenericEventCountEntities().get(k).getLinkTableId().gettable2Id().equals(app.getRules().get(i).getEventName())){
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
    public synchronized ResponseEntity<List<Badge>> getBadges(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "apiToken", required = true)  String infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos);
        //check if the app exists, if not we send a 404 error
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        //retrieve the list of all badges in the app
        List<BadgeEntity> badgeEntities = app.getBadges();
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeEntities) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    @Transactional
    public synchronized ResponseEntity<Badge> updateBadge(@NotNull @ApiParam(value = "" ,required=true )  @Valid @RequestBody UpdateBadge updatebadge) {
        BadgeEntity oldBadge = new BadgeEntity();
        oldBadge.setCompositeId(new CompositeId(updatebadge.getNewBadge().getApiToken(), updatebadge.getOldName()));

        BadgeEntity newBadge = toBadgeEntity(updatebadge.getNewBadge());
        ApplicationEntity app = applicationRepository.findByApiToken(newBadge.getCompositeId().getApiToken());

        //check if the app exists, if not, we send a 404 error
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        else{
            int index;
            //check if the app contains the badge we try to update and that the new badge doesn't exist, if not we send a 404 error
            if((index = app.getBadges().indexOf(oldBadge)) != -1){
                if(app.getBadges().contains(newBadge)){
                    return ResponseEntity.status(403).build();
                }
                //replace the old badge by the new one
                app.getBadges().set(index, newBadge);
                //for each rule of the app, we update the badge
                for(RuleEntity r : app.getRules()){
                    for(RuleAwardsBadgesEntity b : r.getAwards().getRuleAwardsBadgesId()){
                        if(b.getRuleBadgesId().gettable2Id().equals(oldBadge.getCompositeId().getName())) {
                            b.setRuleBadgesId(new LinkTableId(b.getRuleBadgesId().getApiToken(), b.getRuleBadgesId().gettable1Id(), newBadge.getCompositeId().getName()));
                        }
                    }
                }

                //for each user, we update the badge if he possess it
                for (UserEntity u : app.getUsers()){
                    for (BadgeEntity b : u.getBadges()){
                        if(b.getCompositeId().getName().equals(oldBadge.getCompositeId().getName())){
                            b.setCompositeId(newBadge.getCompositeId());
                        }
                    }
                }
                applicationRepository.save(app);
                return ResponseEntity.ok(toBadge(newBadge));
            }
            return ResponseEntity.notFound().build();
        }
    }

    private BadgeEntity toBadgeEntity(@NotNull Badge badge) {
        BadgeEntity entity = new BadgeEntity(new CompositeId(badge.getApiToken(), badge.getName()));
        return entity;
    }

    private Badge toBadge(@NotNull BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setApiToken(entity.getCompositeId().getApiToken());
        badge.setName(entity.getCompositeId().getName());
        return badge;
    }

}
