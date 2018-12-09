package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.*;
import io.avalia.gamification.api.RulesApi;
import io.avalia.gamification.api.model.AppInfos;
import io.avalia.gamification.api.model.PointScale;
import io.avalia.gamification.api.model.Rule;
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
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule Rule) {
        RuleEntity newRuleEntity = toRuleEntity(Rule);
        ApplicationEntity app = applicationRepository.findByApiToken(Rule.getApiToken());
        if (app == null) {
            app = new ApplicationEntity(Rule.getApiToken());
        } else {
            List<RuleEntity> Rules = app.getRules();
            for (int i = 0; i < Rules.size(); i++) {
                if (Rules.get(i).getId().equals(newRuleEntity.getId())) {
                    return ResponseEntity.status(304).build();
                }
            }
        }
        app.addRule(newRuleEntity);

        //check if the badges awarded by the rule exits, if they don't create them
        List<BadgeEntity> badges = newRuleEntity.getAwards().getBadge();
        for(int i = 0; i < badges.size(); i++){
            if(app.getBadges().indexOf(badges.get(i)) == -1){
                app.addBadge(badges.get(i));
            }
        }

        //check if the PointScales concerned by the rule exist, of they don't create them
        List<PointScaleEntity> pointScales = newRuleEntity.getAwards().getPoint();
        List<UserEntity> users = userRepository.findByIdApiToken(Rule.getApiToken());
        for(int i = 0; i < pointScales.size(); i++){
            //if the pointScale doesn't exist, we have to add it and to instantiate it for each user of the app
            if(app.getPointScales().indexOf(pointScales.get(i)) == -1){
                app.addPointScale(pointScales.get(i));
                for (UserEntity u: users) {
                    u.addPointScale(pointScales.get(i));
                }
            }
        }
        applicationRepository.save(app);
        CompositeId id = newRuleEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId().getApiToken() + newRuleEntity.getId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Rule>> getRules(AppInfos infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos.getApiToken());
        if(app == null){
            return ResponseEntity.notFound().build();
        }
        List<RuleEntity> RuleEntities = app.getRules();
        List<Rule> Rules = new ArrayList<>();
        for (RuleEntity RuleEntity : RuleEntities) {
            Rules.add(toRule(RuleEntity));
        }
        return ResponseEntity.ok(Rules);
    }


    private RuleEntity toRuleEntity(Rule Rule) {
        RuleEntity entity = new RuleEntity(new CompositeId(Rule.getApiToken(), Rule.getName()));

        RuleAwardsEntity rae = new RuleAwardsEntity();
        List<BadgeEntity> Badges = new ArrayList<>();
        List<PointScaleEntity> points = new ArrayList<>();

        for (io.avalia.gamification.api.model.Badge b : Rule.getAwards().getBadge()) {
            BadgeEntity BadgeEntity = new BadgeEntity(new CompositeId(b.getApiToken(), b.getName()));
            Badges.add(BadgeEntity);
        }

        for (io.avalia.gamification.api.model.PointScale p : Rule.getAwards().getPoint()) {
            PointScaleEntity pointScaleEntity = new PointScaleEntity(new CompositeId(p.getApiToken(), p.getName()));
            points.add(pointScaleEntity);
        }
        rae.setBadge(Badges);
        rae.setPoint(points);
        rae.setAmountofPoint(Rule.getAwards().getAmountofPoint());

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
        Rule.setApiToken(entity.getId().getApiToken());
        Rule.setName(entity.getId().getName());


        io.avalia.gamification.api.model.RuleAwards ra = new io.avalia.gamification.api.model.RuleAwards();
        List<io.avalia.gamification.api.model.Badge> Badges = new ArrayList<>();
        List<io.avalia.gamification.api.model.PointScale> points = new ArrayList<>();

        for (BadgeEntity b : entity.getAwards().getBadge()) {
            io.avalia.gamification.api.model.Badge Badge = new io.avalia.gamification.api.model.Badge();
            Badge.setApiToken(b.getId().getApiToken());
            Badge.setName(b.getId().getName());
            Badges.add(Badge);
        }

        for (PointScaleEntity p : entity.getAwards().getPoint()) {
            io.avalia.gamification.api.model.PointScale pointScale = new PointScale();
            pointScale.setApiToken(p.getId().getApiToken());
            pointScale.setName(p.getId().getName());
            points.add(pointScale);
        }
        ra.setBadge(Badges);
        ra.setPoint(points);
        ra.setAmountofPoint(entity.getAwards().getAmountofPoint());
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
