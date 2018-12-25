package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.entities.PointScaleEntity;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.UserRepository;
import io.avalia.gamification.api.PointScalesApi;
import io.avalia.gamification.api.model.PointScale;
import io.avalia.gamification.api.model.AppInfos;
import io.avalia.gamification.api.model.UpdatePointScale;
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
public class PointScalesApiController implements PointScalesApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> createPointScale(@ApiParam(value = "", required = true) @Valid @RequestBody PointScale PointScale) {
        PointScaleEntity newPointScaleEntity = toPointScaleEntity(PointScale);
        ApplicationEntity app = applicationRepository.findByApiToken(PointScale.getApiToken());
        if(app == null){
            app = new ApplicationEntity(PointScale.getApiToken());
        }
        else{
            List<PointScaleEntity> PointScales = app.getPointScales();
            for (int i = 0; i < PointScales.size(); i++){
                if(PointScales.get(i).getId().equals(newPointScaleEntity.getId())){
                    return ResponseEntity.status(304).build();
                }
            }
        }
        app.addPointScale(newPointScaleEntity);
        for (UserEntity user : app.getUsers()) {
            UserEntity u = userRepository.findById(user.getId());
            u.addPointScale(newPointScaleEntity);
        }
        applicationRepository.save(app);
        CompositeId id = newPointScaleEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScaleEntity.getId().getApiToken() + newPointScaleEntity.getId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<PointScale> deletePointScale(PointScale pointScale) {
        PointScaleEntity pointScaleEntity = toPointScaleEntity(pointScale);
        ApplicationEntity app = applicationRepository.findByApiToken(pointScaleEntity.getId().getApiToken());
        if (app == null || app.getPointScales().indexOf(pointScaleEntity) == -1) {
            return ResponseEntity.notFound().build();
        } else {
            //for each user remove the badge
            for(int i = 0; i < app.getUsers().size(); i++){
                UserEntity u = app.getUsers().get(i);
                int index;
                if((index = u.getPointScales().indexOf(pointScaleEntity)) != -1){
                    u.getBadges().remove(index);
                }
                if((index = u.getUserPointScaleEntities().indexOf(new LinkTableId(pointScale.getApiToken(), u.getId().getName(), pointScale.getName()))) != -1){
                    u.getUserPointScaleEntities().remove(index);
                }
            }
            //for each rules remove the badge
            int indexPointScale = 0;
            for(int i = 0; i < app.getRules().size(); i++){
                RuleEntity r = app.getRules().get(i);
                LinkTableId linkTableId = new LinkTableId(r.getId().getApiToken(), r.getId().getName(), pointScaleEntity.getId().getName());
                for(int j = 0; j < r.getAwards().getruleAwardsPointScaleId().size(); j++) {
                    if (r.getAwards().getruleAwardsPointScaleId().get(j).getRulePointScaleId().equals(linkTableId)) {
                        r.getAwards().getruleAwardsPointScaleId().remove(j);
                        List<Integer> newPoint = r.getAwards().getAmountofPoint();
                        newPoint.remove(j);
                        r.getAwards().setAmountofPoint(newPoint);
                        j--;
                    }
                }
                //if the rule become empty, we removed it
                if (r.getAwards().getRuleAwardsBadgesId().size() == 0 && r.getAwards().getruleAwardsPointScaleId().size() == 0) {
                    app.getRules().remove(i);
                    i--;
                }
            }
            //remove the badge from the app
            for(int i = 0; i < app.getPointScales().size(); i++){
                if((indexPointScale = app.getPointScales().indexOf(pointScaleEntity)) != -1){
                    app.getPointScales().remove(indexPointScale);
                    i--;
                }
            }
            applicationRepository.save(app);
            return ResponseEntity.ok(pointScale);
        }
    }

    @Override
    public ResponseEntity<List<PointScale>> getPointScales(AppInfos infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos.getApiToken());
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        List<PointScaleEntity> PointScaleEntities = app.getPointScales();
        List<PointScale> PointScales = new ArrayList<>();
        for (PointScaleEntity PointScaleEntity : PointScaleEntities) {
            PointScales.add(toPointScale(PointScaleEntity));
        }
        return ResponseEntity.ok(PointScales);
    }

    @Override
    public ResponseEntity<PointScale> updatePointScale(UpdatePointScale updatePointScale) {
        PointScaleEntity oldPointScale = new PointScaleEntity();
        oldPointScale.setId(new CompositeId(updatePointScale.getNewPointScale().getApiToken(), updatePointScale.getOldName()));
        PointScaleEntity newPointScale = toPointScaleEntity(updatePointScale.getNewPointScale());
        ApplicationEntity app = applicationRepository.findByApiToken(newPointScale.getId().getApiToken());
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        else{
            int index;
            if((index = app.getPointScales().indexOf(oldPointScale)) != -1){
                app.getPointScales().set(index, newPointScale);
                for(RuleEntity r : app.getRules()){
                    for(RuleAwardsBadgesEntity b : r.getAwards().getRuleAwardsBadgesId()){
                        if(b.getRuleBadgesId().gettable2Id().equals(oldPointScale.getId().getName())) {
                            b.setRuleBadgesId(new LinkTableId(b.getRuleBadgesId().getApiToken(), b.getRuleBadgesId().gettable1Id(), newPointScale.getId().getName()));
                        }
                    }
                }

                for (UserEntity u : app.getUsers()){
                    for (BadgeEntity b : u.getBadges()){
                        if(b.getId().getName().equals(oldPointScale.getId().getName())){
                            b.setId(newPointScale.getId());
                        }
                    }
                    for (UserPointScaleEntity upse : u.getUserPointScaleEntities()){
                        if(upse.getUserPointScaleId().gettable2Id().equals(oldPointScale.getId().getName())){
                            upse.getUserPointScaleId().setTable2Id(newPointScale.getId().getName());
                        }
                    }
                }
                applicationRepository.save(app);
                return ResponseEntity.ok(toPointScale(newPointScale));
            }
            return ResponseEntity.notFound().build();
        }
    }

    private PointScaleEntity toPointScaleEntity(PointScale PointScale) {
        PointScaleEntity entity = new PointScaleEntity(new CompositeId(PointScale.getApiToken(), PointScale.getName()));
        return entity;
    }

    private PointScale toPointScale(PointScaleEntity entity) {
        PointScale PointScale = new PointScale();
        PointScale.setApiToken(entity.getId().getApiToken());
        PointScale.setName(entity.getId().getName());
        return PointScale;
    }

}
