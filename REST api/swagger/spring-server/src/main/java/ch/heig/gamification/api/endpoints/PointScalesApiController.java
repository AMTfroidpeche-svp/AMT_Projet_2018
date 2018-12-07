package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.PointScaleEntity;
import io.avalia.gamification.api.PointScalesApi;
import io.avalia.gamification.api.model.PointScale;
import io.avalia.gamification.api.model.Infos;
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
    PointScaleRepository PointScaleRepository;

    public ResponseEntity<Object> createPointScale(@ApiParam(value = "", required = true) @Valid @RequestBody PointScale PointScale) {
        PointScaleEntity newPointScaleEntity = toPointScaleEntity(PointScale);
        PointScaleRepository.save(newPointScaleEntity);
        long id = newPointScaleEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<PointScale>> getPointScales(Infos infos) {
        List<PointScale> PointScales = new ArrayList<>();
        for (PointScaleEntity PointScaleEntity : PointScaleRepository.findAll()) {
            PointScales.add(toPointScale(PointScaleEntity));
        }
        return ResponseEntity.ok(PointScales);
    }

    private PointScaleEntity toPointScaleEntity(PointScale PointScale) {
        PointScaleEntity entity = new PointScaleEntity();
        entity.setApiToken(PointScale.getApiToken());
        entity.setName(PointScale.getName());
        entity.setUserId(PointScale.getUserId());
        entity.setValue(PointScale.getValue());
        return entity;
    }

    private PointScale toPointScale(PointScaleEntity entity) {
        PointScale PointScale = new PointScale();
        PointScale.setApiToken(entity.getApiToken());
        PointScale.setName(entity.getName());
        PointScale.setUserId(entity.getUserId());
        PointScale.setValue(entity.getValue());
        return PointScale;
    }

}
