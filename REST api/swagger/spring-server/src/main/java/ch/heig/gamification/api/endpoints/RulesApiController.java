package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import io.avalia.gamification.api.RulesApi;
import io.avalia.gamification.api.model.Infos;
import io.avalia.gamification.api.model.Rule;
import io.avalia.gamification.api.model.RuleAwardsPoint;
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
public class RulesApiController implements RulesApi {

    @Autowired
    ch.heig.gamification.repositories.RuleRepository RuleRepository;

    @Override
    public ResponseEntity<Object> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule Rule) {
        RuleEntity newRuleEntity = toRuleEntity(Rule);
        RuleRepository.save(newRuleEntity);
        long id = newRuleEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Rule>> getRules(Infos infos) {
        List<Rule> Rules = new ArrayList<>();
        for (RuleEntity RuleEntity : RuleRepository.findAll()) {
            Rules.add(toRule(RuleEntity));
        }
        return ResponseEntity.ok(Rules);
    }


    private RuleEntity toRuleEntity(Rule Rule) {
        RuleEntity entity = new RuleEntity();
        entity.setApiToken(Rule.getApiToken());
        entity.setName(Rule.getName());

        RuleAwardsEntity rae = new RuleAwardsEntity();
        List<BadgeEntity> badges = new ArrayList<>();
        List<RuleAwardsPointEntity> points = new ArrayList<>();

        for (io.avalia.gamification.api.model.Badge b : Rule.getAwards().getBadge()) {
            BadgeEntity badgeEntity = new BadgeEntity();
            badgeEntity.setApiToken(b.getApiToken());
            badgeEntity.setName(b.getName());
            badgeEntity.setUserId(b.getUserId());
            badges.add(badgeEntity);
        }

        for (io.avalia.gamification.api.model.RuleAwardsPoint p : Rule.getAwards().getPoint()) {
            RuleAwardsPointEntity RuleAwardsPointEntity = new RuleAwardsPointEntity();

            io.avalia.gamification.api.model.PointScale pointScale = p.getPointScale();
            PointScaleEntity pointScaleEntity = new PointScaleEntity();
            pointScaleEntity.setApiToken(pointScale.getApiToken());
            pointScaleEntity.setName(pointScale.getName());
            pointScaleEntity.setUserId(pointScale.getUserId());
            pointScaleEntity.setValue(pointScale.getValue());

            RuleAwardsPointEntity.setPointScaleEntity(pointScaleEntity);
            RuleAwardsPointEntity.setValue(p.getAmount());
            points.add(RuleAwardsPointEntity);
        }
        rae.setBadge(badges);
        rae.setPoint(points);
        entity.setAwards(rae);

        List<RulePropertiesEntity> properties = new ArrayList<>();

        for (io.avalia.gamification.api.model.RuleProperties p : Rule.getProperties()) {
            RulePropertiesEntity propertiesEntity = new RulePropertiesEntity();
            propertiesEntity.setType(p.getType());
            propertiesEntity.setCompareOperator(p.getCompareOperator());
            propertiesEntity.setValue(p.getValue());
            properties.add(propertiesEntity);
        }
        entity.setProperties(properties);
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule Rule = new Rule();
        Rule.setApiToken(entity.getApiToken());
        Rule.setName(entity.getName());


        io.avalia.gamification.api.model.RuleAwards ra = new io.avalia.gamification.api.model.RuleAwards();
        List<io.avalia.gamification.api.model.Badge> badges = new ArrayList<>();
        List<io.avalia.gamification.api.model.RuleAwardsPoint> points = new ArrayList<>();

        for (BadgeEntity b : entity.getAwards().getBadge()) {
            io.avalia.gamification.api.model.Badge badge = new io.avalia.gamification.api.model.Badge();
            badge.setApiToken(b.getApiToken());
            badge.setName(b.getName());
            badge.setUserId(b.getUserId());
            badges.add(badge);
        }

        for (RuleAwardsPointEntity p : entity.getAwards().getPoint()) {
            io.avalia.gamification.api.model.RuleAwardsPoint RuleAwardsPoint = new RuleAwardsPoint();

            PointScaleEntity pointScaleEntity = p.getPointScaleEntity();
            io.avalia.gamification.api.model.PointScale pointScale = new io.avalia.gamification.api.model.PointScale();
            pointScale.setApiToken(pointScaleEntity.getApiToken());
            pointScale.setName(pointScaleEntity.getName());
            pointScale.setUserId(pointScaleEntity.getUserId());
            pointScale.setValue(pointScaleEntity.getValue());

            RuleAwardsPoint.setPointScale(pointScale);
            RuleAwardsPoint.setAmount(p.getValue());
            points.add(RuleAwardsPoint);
        }
        ra.setBadge(badges);
        ra.setPoint(points);
        Rule.setAwards(ra);

        List<io.avalia.gamification.api.model.RuleProperties> properties = new ArrayList<>();

        for (RulePropertiesEntity p : entity.getProperties()) {
            io.avalia.gamification.api.model.RuleProperties Properties = new io.avalia.gamification.api.model.RuleProperties();
            Properties.setType(p.getType());
            Properties.setCompareOperator(p.getCompareOperator());
            Properties.setValue(p.getValue());
            properties.add(Properties);
        }
        Rule.setProperties(properties);

        return Rule;
    }

}
