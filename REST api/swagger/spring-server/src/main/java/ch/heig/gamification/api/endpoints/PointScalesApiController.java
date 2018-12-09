package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.entities.PointScaleEntity;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.UserRepository;
import io.avalia.gamification.api.PointScalesApi;
import io.avalia.gamification.api.model.PointScale;
import io.avalia.gamification.api.model.AppInfos;
import ch.heig.gamification.repositories.PointScaleRepository;
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
