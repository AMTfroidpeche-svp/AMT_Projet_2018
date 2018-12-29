package ch.heig.gamification.api.endpoints;

/**
 * File : PointScalesApiController.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This controller is used to operate CRUD operations on pointScales
 */

import ch.heig.gamification.entities.*;
import ch.heig.gamification.entities.PointScaleEntity;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.UserRepository;
import ch.heig.gamification.api.PointScalesApi;
import ch.heig.gamification.api.model.PointScale;
import ch.heig.gamification.api.model.UpdatePointScale;
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
public class PointScalesApiController implements PointScalesApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public ResponseEntity<Object> createPointScale(@NotNull @ApiParam(value = "", required = true) @Valid @RequestBody PointScale PointScale) {
        PointScaleEntity newPointScaleEntity = toPointScaleEntity(PointScale);
        ApplicationEntity app = applicationRepository.findByApiToken(PointScale.getApiToken());
        if(app == null){
            app = new ApplicationEntity(PointScale.getApiToken());
        }
        else{
            List<PointScaleEntity> PointScales = app.getPointScales();
            for (int i = 0; i < PointScales.size(); i++){
                if(PointScales.get(i).getCompositeId().equals(newPointScaleEntity.getCompositeId())){
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
        CompositeId id = newPointScaleEntity.getCompositeId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScaleEntity.getCompositeId().getApiToken() + newPointScaleEntity.getCompositeId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @Transactional
    public ResponseEntity<PointScale> deletePointScale(@NotNull @ApiParam(value = "", required = true) @Valid @RequestBody PointScale pointScale) {
        PointScaleEntity pointScaleEntity = toPointScaleEntity(pointScale);
        ApplicationEntity app = applicationRepository.findByApiToken(pointScaleEntity.getCompositeId().getApiToken());
        if (app == null || app.getPointScales().indexOf(pointScaleEntity) == -1) {
            return ResponseEntity.notFound().build();
        } else {
            //for each user remove the badge
            for(int i = 0; i < app.getUsers().size(); i++){
                UserEntity u = app.getUsers().get(i);
                int index;
                if((index = u.getPointScales().indexOf(pointScaleEntity)) != -1){
                    u.getPointScales().remove(index);
                }
                for(int j = 0; j < u.getUserPointScaleEntities().size(); j++){
                    if (u.getUserPointScaleEntities().get(j).getUserPointScaleId().gettable2Id().equals(pointScale.getName())){
                        u.getUserPointScaleEntities().remove(j);
                        j--;
                    }
                }
                if((index = u.getUserPointScaleEntities().indexOf(new LinkTableId(pointScale.getApiToken(), u.getId().getName(), pointScale.getName()))) != -1){
                    u.getUserPointScaleEntities().remove(index);
                }
            }
            //for each rules remove the badge
            int indexPointScale = 0;
            for(int i = 0; i < app.getRules().size(); i++){
                RuleEntity r = app.getRules().get(i);
                LinkTableId linkTableId = new LinkTableId(r.getCompositeId().getApiToken(), r.getCompositeId().getName(), pointScaleEntity.getCompositeId().getName());
                for(int j = 0; j < r.getAwards().getruleAwardsPointScaleId().size(); j++) {
                    if (r.getAwards().getruleAwardsPointScaleId().get(j).getRulePointScaleId().equals(linkTableId)) {
                        r.getAwards().getruleAwardsPointScaleId().remove(j);
                        List<Integer> newPoint = r.getAwards().getAmountofPoint();
                        newPoint.remove(j);
                        r.getAwards().setAmountofPoint(newPoint);
                        j--;
                    }
                }
                //if the rule become empty, we removed it and all event count link to it
                if (r.getAwards().getRuleAwardsBadgesId().size() == 0 && r.getAwards().getruleAwardsPointScaleId().size() == 0) {
                    int ruleDealingWithEvent = 0;
                    String eventName = app.getRules().get(i).getEventName();
                    for(int k = 0; k < app.getRules().size(); k++){
                        if(app.getRules().get(k).getEventName().equals(eventName)){
                            ruleDealingWithEvent++;
                        }
                    }
                    //with delete the event count only if this was the last rule dealing with this event
                    if(ruleDealingWithEvent == 1) {
                        for (UserEntity userEntity : app.getUsers()) {
                            for (int k = 0; k < userEntity.getUserGenericEventCountEntities().size(); k++) {
                                if (userEntity.getUserGenericEventCountEntities().get(k).getLinkTableId().gettable2Id().equals(app.getRules().get(i).getEventName())) {
                                    userEntity.getUserGenericEventCountEntities().remove(k);
                                }
                            }
                        }
                    }
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
    @Transactional
    public ResponseEntity<List<PointScale>> getPointScales(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "apiToken", required = true)  String infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos);
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
    @Transactional
    public ResponseEntity<PointScale> updatePointScale(@NotNull @ApiParam(value = "", required = true) @Valid @RequestBody UpdatePointScale updatePointScale) {
        PointScaleEntity oldPointScale = new PointScaleEntity();
        oldPointScale.setCompositeId(new CompositeId(updatePointScale.getNewPointScale().getApiToken(), updatePointScale.getOldName()));
        PointScaleEntity newPointScale = toPointScaleEntity(updatePointScale.getNewPointScale());
        ApplicationEntity app = applicationRepository.findByApiToken(newPointScale.getCompositeId().getApiToken());
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        else{
            int index;
            if((index = app.getPointScales().indexOf(oldPointScale)) != -1){
                app.getPointScales().set(index, newPointScale);
                for(RuleEntity r : app.getRules()){
                    for(RuleAwardsPointScaleEntity p : r.getAwards().getruleAwardsPointScaleId()){
                        if(p.getRulePointScaleId().gettable2Id().equals(oldPointScale.getCompositeId().getName())) {
                            p.setRulePointScaleId(new LinkTableId(p.getRulePointScaleId().getApiToken(), p.getRulePointScaleId().gettable1Id(), newPointScale.getCompositeId().getName()));
                        }
                    }
                }

                for (UserEntity u : app.getUsers()){
                    for (PointScaleEntity p : u.getPointScales()){
                        if(p.getCompositeId().getName().equals(oldPointScale.getCompositeId().getName())){
                            p.setCompositeId(newPointScale.getCompositeId());
                        }
                    }
                    for (UserPointScaleEntity upse : u.getUserPointScaleEntities()){
                        if(upse.getUserPointScaleId().gettable2Id().equals(oldPointScale.getCompositeId().getName())){
                            upse.getUserPointScaleId().setTable2Id(newPointScale.getCompositeId().getName());
                        }
                    }
                }
                applicationRepository.save(app);
                return ResponseEntity.ok(toPointScale(newPointScale));
            }
            return ResponseEntity.notFound().build();
        }
    }

    private PointScaleEntity toPointScaleEntity(@NotNull PointScale PointScale) {
        PointScaleEntity entity = new PointScaleEntity(new CompositeId(PointScale.getApiToken(), PointScale.getName()));
        return entity;
    }

    private PointScale toPointScale(@NotNull PointScaleEntity entity) {
        PointScale PointScale = new PointScale();
        PointScale.setApiToken(entity.getCompositeId().getApiToken());
        PointScale.setName(entity.getCompositeId().getName());
        return PointScale;
    }

}
