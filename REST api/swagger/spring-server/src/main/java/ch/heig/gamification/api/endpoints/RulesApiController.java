package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.*;
import io.avalia.gamification.api.RulesApi;
import io.avalia.gamification.api.model.AppInfos;
import io.avalia.gamification.api.model.Rule;
import io.avalia.gamification.api.model.RuleInfos;
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
        //we can't have a different number of point and point scales
        if(newRuleEntity.getAwards().getAmountofPoint() != null && newRuleEntity.getAwards().getAmountofPoint().size() != newRuleEntity.getAwards().getruleAwardsPointScaleId().size()){
            return ResponseEntity.badRequest().build();
        }
        ApplicationEntity app = applicationRepository.findByApiToken(Rule.getApiToken());
        if (app == null) {
            app = new ApplicationEntity(Rule.getApiToken());
        } else {
            List<RuleEntity> Rules = app.getRules();
            for (int i = 0; i < Rules.size(); i++) {
                if (Rules.get(i).equals(newRuleEntity)) {
                    return ResponseEntity.status(304).build();
                }
            }
        }
        app.addRule(newRuleEntity);
        addDependenciesForRule(app, newRuleEntity);

        applicationRepository.save(app);
        CompositeId id = newRuleEntity.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId().getApiToken() + newRuleEntity.getId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<RuleInfos> deleteRule(RuleInfos rule) {
        ApplicationEntity app = applicationRepository.findByApiToken(rule.getApiToken());
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        List<RuleEntity> RuleEntities = app.getRules();
        for (int i = 0; i < RuleEntities.size(); i++) {
            if (RuleEntities.get(i).getId().getName().equals(rule.getName())) {
                CompositeId deleted = new CompositeId(RuleEntities.get(i).getId());
                RuleEntities.remove(i);
                applicationRepository.save(app);
                return ResponseEntity.ok(toRuleInfos(deleted));
            }
        }
        return ResponseEntity.ok(new RuleInfos());
    }

    @Override
    public ResponseEntity<List<Rule>> getRules(AppInfos infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos.getApiToken());
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        List<RuleEntity> RuleEntities = app.getRules();
        List<Rule> Rules = new ArrayList<>();
        for (RuleEntity RuleEntity : RuleEntities) {
            Rules.add(toRule(RuleEntity));
        }
        return ResponseEntity.ok(Rules);
    }

    @Override
    public ResponseEntity<Rule> updateRule(io.avalia.gamification.api.model.UpdateRule updateRule) {
        RuleEntity oldRule = new RuleEntity();
        RuleEntity newRule = toRuleEntity(updateRule.getNewRule());
        //we can't have a different number of point and point scales
        if(newRule.getAwards().getAmountofPoint().size() != newRule.getAwards().getruleAwardsPointScaleId().size()){
            return ResponseEntity.badRequest().build();
        }
        oldRule.setId(new CompositeId(updateRule.getNewRule().getApiToken(), updateRule.getOldName()));
        ApplicationEntity app = applicationRepository.findByApiToken(newRule.getId().getApiToken());
        if (app == null) {
            return ResponseEntity.notFound().build();
        } else {
            int index;
            if ((index = app.getRules().indexOf(oldRule)) != -1) {
                app.getRules().set(index, newRule);
                addDependenciesForRule(app, newRule);

                applicationRepository.save(app);
                return ResponseEntity.ok(toRule(newRule));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }


    private RuleEntity toRuleEntity(Rule Rule) {
        RuleEntity entity = new RuleEntity(new CompositeId(Rule.getApiToken(), Rule.getRuleName()));
        entity.setEventName(Rule.getEventName());

        RuleAwardsEntity rae = new RuleAwardsEntity();
        rae.setId(new CompositeId(Rule.getApiToken(), Rule.getRuleName()));
        List<RuleAwardsBadgesEntity> Badges = new ArrayList<>();
        List<RuleAwardsPointScaleEntity> points = new ArrayList<>();

        for (String badgeName : Rule.getAwards().getBadge()) {
            BadgeEntity BadgeEntity = new BadgeEntity(new CompositeId(Rule.getApiToken(), badgeName));
            Badges.add(new RuleAwardsBadgesEntity(new LinkTableId(Rule.getApiToken(), rae.getId().getName(), BadgeEntity.getId().getName())));
        }

        for (String pointScaleName : Rule.getAwards().getPoint()) {
            PointScaleEntity pointScaleEntity = new PointScaleEntity(new CompositeId(Rule.getApiToken(), pointScaleName));
            points.add(new RuleAwardsPointScaleEntity(new LinkTableId(Rule.getApiToken(), rae.getId().getName(), pointScaleEntity.getId().getName())));
        }
        rae.setRuleAwardsBadgesId(Badges);
        rae.setruleAwardsPointScaleId(points);
        rae.setAmountofPoint(Rule.getAwards().getAmountofPoint());

        entity.setAwards(rae);

        List<RulePropertiesEntity> properties = new ArrayList<>();

        for (io.avalia.gamification.api.model.RuleProperties p : Rule.getProperties()) {
            int i = 1;
            RulePropertiesEntity propertiesEntity = new RulePropertiesEntity();
            propertiesEntity.setPropertyName(p.getName());
            propertiesEntity.setType(p.getType());
            propertiesEntity.setCompareOperator(p.getCompareOperator());
            propertiesEntity.setValue(p.getValue());
            propertiesEntity.setId(new CompositeId(Rule.getApiToken(), "ruleProperty" + Rule.getRuleName() + String.valueOf(i)));
            i++;
            properties.add(propertiesEntity);
        }
        entity.setProperties(properties);
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule Rule = new Rule();
        Rule.setApiToken(entity.getId().getApiToken());
        Rule.setRuleName(entity.getId().getName());
        Rule.setEventName(entity.getEventName());


        io.avalia.gamification.api.model.RuleAwards ra = new io.avalia.gamification.api.model.RuleAwards();
        List<String> Badges = new ArrayList<>();
        List<String> points = new ArrayList<>();

        for (RuleAwardsBadgesEntity rabe : entity.getAwards().getRuleAwardsBadgesId()) {
            Badges.add(rabe.getRuleBadgesId().gettable2Id());
        }

        for (RuleAwardsPointScaleEntity rapse : entity.getAwards().getruleAwardsPointScaleId()) {
            points.add(rapse.getRulePointScaleId().gettable2Id());
        }
        ra.setBadge(Badges);
        ra.setPoint(points);
        ra.setAmountofPoint(entity.getAwards().getAmountofPoint());
        Rule.setAwards(ra);

        List<io.avalia.gamification.api.model.RuleProperties> properties = new ArrayList<>();

        for (RulePropertiesEntity p : entity.getProperties()) {
            io.avalia.gamification.api.model.RuleProperties Properties = new io.avalia.gamification.api.model.RuleProperties();
            Properties.setName(p.getPropertyName());
            Properties.setType(p.getType());
            Properties.setCompareOperator(p.getCompareOperator());
            Properties.setValue(p.getValue());
            properties.add(Properties);
        }
        Rule.setProperties(properties);

        return Rule;
    }

    private RuleInfos toRuleInfos(CompositeId c) {
        RuleInfos ruleInfos = new RuleInfos();
        ruleInfos.setApiToken(c.getApiToken());
        ruleInfos.setName(c.getName());
        return ruleInfos;
    }

    private void addDependenciesForRule(ApplicationEntity app, RuleEntity r){
        //check if the badges awarded by the rule exits, if they don't create them
        List<RuleAwardsBadgesEntity> badges = r.getAwards().getRuleAwardsBadgesId();
        if(badges != null) {
            for (int i = 0; i < badges.size(); i++) {
                BadgeEntity be = new BadgeEntity(new CompositeId(badges.get(i).getRuleBadgesId().getApiToken(), badges.get(i).getRuleBadgesId().gettable2Id()));
                if (app.getBadges().indexOf(be) == -1) {
                    app.addBadge(be);
                }
            }
        }

        //check if the PointScales concerned by the rule exist, of they don't create them
        List<RuleAwardsPointScaleEntity> pointScales = r.getAwards().getruleAwardsPointScaleId();
        List<UserEntity> users = userRepository.findByIdApiToken(r.getId().getApiToken());
        if(pointScales != null) {
            for (int i = 0; i < pointScales.size(); i++) {
                PointScaleEntity pse = new PointScaleEntity(new CompositeId(pointScales.get(i).getRulePointScaleId().getApiToken(), pointScales.get(i).getRulePointScaleId().gettable2Id()));
                //if the pointScale doesn't exist, we have to add it and to instantiate it for each user of the app
                if (app.getPointScales().indexOf(pse) == -1) {
                    app.addPointScale(pse);
                    for (UserEntity u : users) {
                        u.addPointScale(pse);
                    }
                }
            }
        }
    }
}
