package ch.heig.gamification.api.endpoints;


import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.avalia.gamification.api.model.User;
import io.avalia.gamification.api.model.UserInfos;
import io.avalia.gamification.api.UsersApi;
import io.avalia.gamification.api.model.Badge;
import io.avalia.gamification.api.model.PointScale;
import io.avalia.gamification.api.model.UserPointScale;
import io.avalia.gamification.api.model.UserEventCount;

import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<User> getUser(UserInfos infos) {
        UserEntity user = userRepository.findById(new CompositeId(infos.getApiToken(), infos.getName()));
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toUser(user));
    }

    private UserEntity toUserEntity(User User) {
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
            UserPointScaleEntity userPointScaleEntity = new UserPointScaleEntity((LinkTableId)userPointScale.getLinkTableId());
            userPointScaleEntity.setValue(userPointScale.getValue());
            userPointScales.add(userPointScaleEntity);
        }

        for (UserEventCount userEventCount : User.getUserEventCount()) {
            UserGenericEventCountEntity userGenericEventCountEntity = new UserGenericEventCountEntity((LinkTableId)userEventCount.getLinkTableId());
            userGenericEventCountEntity.setValue(userEventCount.getValue());
            userGenereicCountEvents.add(userGenericEventCountEntity);
        }

        entity.setBadges(Badges);
        entity.setPointScales(pointScales);
        entity.setUserPointScaleEntities(userPointScales);
        entity.setUserGenericEventCountEntities(userGenereicCountEvents);

        return entity;
    }

    private io.avalia.gamification.api.model.User toUser(UserEntity entity) {
        User User = new User();
        User.setApiToken(entity.getId().getApiToken());
        User.setName(entity.getId().getName());

        List<Badge> Badges = new ArrayList<>();
        List<PointScale> pointScales = new ArrayList<>();
        List<UserPointScale> userPointScales = new ArrayList<>();
        List<UserEventCount> userGenereicCountEvents = new ArrayList<>();

        for (BadgeEntity badgeEntity : entity.getBadges()) {
            Badge badge = new Badge();
            badge.setApiToken(badgeEntity.getId().getApiToken());
            badge.setName(badgeEntity.getId().getName());
            Badges.add(badge);
        }

        for (PointScaleEntity pointScaleEntity : entity.getPointScales()) {
            PointScale pointScale = new PointScale();
            pointScale.setApiToken(pointScaleEntity.getId().getApiToken());
            pointScale.setName(pointScaleEntity.getId().getName());
            pointScales.add(pointScale);
        }

        for (UserPointScaleEntity userPointScaleEntity : entity.getUserPointScaleEntities()) {
            UserPointScale userPointScale = new UserPointScale();
            userPointScale.setLinkTableId((LinkTableId)userPointScaleEntity.getUserPointScaleId());
            userPointScale.setValue(userPointScaleEntity.getValue());
            userPointScales.add(userPointScale);
        }

        for (UserGenericEventCountEntity userGenericEventCountEntity : entity.getUserGenericEventCountEntities()) {
            UserEventCount userEventCount = new UserEventCount();
            userEventCount.setLinkTableId((LinkTableId)userGenericEventCountEntity.getId());
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
