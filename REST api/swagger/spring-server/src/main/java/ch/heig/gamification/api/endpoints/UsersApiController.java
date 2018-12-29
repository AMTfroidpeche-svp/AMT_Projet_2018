package ch.heig.gamification.api.endpoints;

/**
 * File : UsersApiController.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This controller is used to retrieve infos about users stored in the database, it only allows to read data
 */

import ch.heig.gamification.api.model.LinkTableId;
import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ch.heig.gamification.api.model.User;
import ch.heig.gamification.api.UsersApi;
import ch.heig.gamification.api.model.Badge;
import ch.heig.gamification.api.model.PointScale;
import ch.heig.gamification.api.model.UserPointScale;
import ch.heig.gamification.api.model.UserEventCount;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<User> getUser(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "apiToken", required = true) String apiToken,
                                        @NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "userName", required = true) String userName) {
        UserEntity user = userRepository.findById(new CompositeId(apiToken, userName));
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toUser(user));
    }

    private UserEntity toUserEntity(@NotNull User User) {
        UserEntity entity = new UserEntity(new CompositeId(User.getApiToken(), User.getName()));

        List<BadgeEntity> Badges = new ArrayList<>();
        List<PointScaleEntity> pointScales = new ArrayList<>();
        List<UserPointScaleEntity> userPointScales = new ArrayList<>();
        List<UserGenericEventCountEntity> userGenereicCountEvents = new ArrayList<>();

        for (Badge badge : User.getBadges()) {
            BadgeEntity BadgeEntity = new BadgeEntity(new CompositeId(badge.getApiToken(), badge.getName()));
            Badges.add(BadgeEntity);
        }

        for (PointScale pointScale : User.getPointScales()) {
            PointScaleEntity pointScaleEntity = new PointScaleEntity(new CompositeId(pointScale.getApiToken(), pointScale.getName()));
            pointScales.add(pointScaleEntity);
        }

        for (UserPointScale userPointScale : User.getUserPointScale()) {
            ch.heig.gamification.entities.LinkTableId linkTableId = new ch.heig.gamification.entities.LinkTableId(userPointScale.getLinkTableId().getApiToken(), userPointScale.getLinkTableId().getTable1Id(), userPointScale.getLinkTableId().getTable2Id());
            UserPointScaleEntity userPointScaleEntity = new UserPointScaleEntity(linkTableId);
            userPointScaleEntity.setValue(userPointScale.getValue());
            userPointScales.add(userPointScaleEntity);
        }

        for (UserEventCount userEventCount : User.getUserEventCount()) {
            ch.heig.gamification.entities.LinkTableId linkTableId = new ch.heig.gamification.entities.LinkTableId(userEventCount.getLinkTableId().getApiToken(), userEventCount.getLinkTableId().getTable1Id(), userEventCount.getLinkTableId().getTable2Id());
            UserGenericEventCountEntity userGenericEventCountEntity = new UserGenericEventCountEntity(linkTableId);
            userGenericEventCountEntity.setValue(userEventCount.getValue());
            userGenereicCountEvents.add(userGenericEventCountEntity);
        }

        entity.setBadges(Badges);
        entity.setPointScales(pointScales);
        entity.setUserPointScaleEntities(userPointScales);
        entity.setUserGenericEventCountEntities(userGenereicCountEvents);

        return entity;
    }

    private User toUser(@NotNull UserEntity entity) {
        User User = new User();
        User.setApiToken(entity.getId().getApiToken());
        User.setName(entity.getId().getName());

        List<Badge> Badges = new ArrayList<>();
        List<PointScale> pointScales = new ArrayList<>();
        List<UserPointScale> userPointScales = new ArrayList<>();
        List<UserEventCount> userGenereicCountEvents = new ArrayList<>();

        for (BadgeEntity badgeEntity : entity.getBadges()) {
            Badge badge = new Badge();
            badge.setApiToken(badgeEntity.getCompositeId().getApiToken());
            badge.setName(badgeEntity.getCompositeId().getName());
            Badges.add(badge);
        }

        for (PointScaleEntity pointScaleEntity : entity.getPointScales()) {
            PointScale pointScale = new PointScale();
            pointScale.setApiToken(pointScaleEntity.getCompositeId().getApiToken());
            pointScale.setName(pointScaleEntity.getCompositeId().getName());
            pointScales.add(pointScale);
        }

        for (UserPointScaleEntity userPointScaleEntity : entity.getUserPointScaleEntities()) {
            UserPointScale userPointScale = new UserPointScale();
            LinkTableId linkTableId = new LinkTableId();
            linkTableId.setApiToken(userPointScaleEntity.getUserPointScaleId().getApiToken());
            linkTableId.setTable1Id(userPointScaleEntity.getUserPointScaleId().gettable1Id());
            linkTableId.setTable2Id(userPointScaleEntity.getUserPointScaleId().gettable2Id());
            userPointScale.setLinkTableId(linkTableId);
            userPointScale.setValue(userPointScaleEntity.getValue());
            userPointScales.add(userPointScale);
        }

        for (UserGenericEventCountEntity userGenericEventCountEntity : entity.getUserGenericEventCountEntities()) {
            UserEventCount userEventCount = new UserEventCount();
            LinkTableId linkTableId = new LinkTableId();
            linkTableId.setApiToken(userGenericEventCountEntity.getLinkTableId().getApiToken());
            linkTableId.setTable1Id(userGenericEventCountEntity.getLinkTableId().gettable1Id());
            linkTableId.setTable2Id(userGenericEventCountEntity.getLinkTableId().gettable2Id());
            userEventCount.setLinkTableId(linkTableId);
            userEventCount.setValue(userGenericEventCountEntity.getValue());
            userGenereicCountEvents.add(userEventCount);
        }

        User.setBadges(Badges);
        User.setPointScales(pointScales);
        User.setUserPointScale(userPointScales);
        User.setUserEventCount(userGenereicCountEvents);

        return User;
    }

}
