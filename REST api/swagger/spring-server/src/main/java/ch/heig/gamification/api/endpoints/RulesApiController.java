package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.*;
import ch.heig.gamification.repositories.*;
import ch.heig.gamification.api.RulesApi;
import ch.heig.gamification.api.model.Rule;
import ch.heig.gamification.api.model.RuleInfos;
import ch.heig.gamification.api.model.UpdateRule;
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
public class RulesApiController implements RulesApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    RuleAwardsRepository ruleAwardsRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
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
        addDependenciesForRule(app, newRuleEntity);
        //ruleAwardsRepository.save(newRuleEntity.getAwards());
        app.addRule(newRuleEntity);

        applicationRepository.save(app);
        CompositeId id = newRuleEntity.getCompositeId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getCompositeId().getApiToken() + newRuleEntity.getCompositeId().getName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @Transactional
    public ResponseEntity<RuleInfos> deleteRule(@ApiParam(value = "", required = true) @Valid @RequestBody RuleInfos rule) {
        ApplicationEntity app = applicationRepository.findByApiToken(rule.getApiToken());
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        List<RuleEntity> RuleEntities = app.getRules();
        for (int i = 0; i < RuleEntities.size(); i++) {
            if (RuleEntities.get(i).getCompositeId().getName().equals(rule.getName())) {
                CompositeId deleted = new CompositeId(RuleEntities.get(i).getCompositeId());
                String eventName = RuleEntities.get(i).getEventName();
                RuleEntities.remove(i);
                removeEventCount(eventName, app);
                applicationRepository.save(app);
                return ResponseEntity.ok(toRuleInfos(deleted));
            }
        }
        return ResponseEntity.ok(new RuleInfos());
    }

    @Override
    @Transactional
    public ResponseEntity<List<Rule>> getRules(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "apiToken", required = true)  String infos) {
        ApplicationEntity app = applicationRepository.findByApiToken(infos);
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
    @Transactional
    public ResponseEntity<Rule> updateRule(@ApiParam(value = "", required = true) @Valid @RequestBody UpdateRule updateRule) {
        RuleEntity oldRule = new RuleEntity();
        RuleEntity newRule = toRuleEntity(updateRule.getNewRule());
        //we can't have a different number of point and point scales
        if(newRule.getAwards().getAmountofPoint().size() != newRule.getAwards().getruleAwardsPointScaleId().size()){
            return ResponseEntity.badRequest().build();
        }
        oldRule.setCompositeId(new CompositeId(updateRule.getNewRule().getApiToken(), updateRule.getOldName()));
        ApplicationEntity app = applicationRepository.findByApiToken(newRule.getCompositeId().getApiToken());
        if (app == null) {
            return ResponseEntity.notFound().build();
        } else {
            int index;
            if ((index = app.getRules().indexOf(oldRule)) != -1) {
                app.getRules().set(index, newRule);
                addDependenciesForRule(app, newRule);
                //if the event concerned by the new rule is different, we have to check if the old rule event is still active
                if(!newRule.getEventName().equals(oldRule.getEventName())){
                    removeEventCount(oldRule.getEventName(), app);
                }

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
        rae.setCompositeId(new CompositeId(Rule.getApiToken(), Rule.getRuleName()));
        List<RuleAwardsBadgesEntity> Badges = new ArrayList<>();
        List<RuleAwardsPointScaleEntity> points = new ArrayList<>();

        for (String badgeName : Rule.getAwards().getBadge()) {
            BadgeEntity BadgeEntity = new BadgeEntity(new CompositeId(Rule.getApiToken(), badgeName));
            Badges.add(new RuleAwardsBadgesEntity(new LinkTableId(Rule.getApiToken(), rae.getCompositeId().getName(), BadgeEntity.getCompositeId().getName())));
        }

        for (String pointScaleName : Rule.getAwards().getPoint()) {
            PointScaleEntity pointScaleEntity = new PointScaleEntity(new CompositeId(Rule.getApiToken(), pointScaleName));
            points.add(new RuleAwardsPointScaleEntity(new LinkTableId(Rule.getApiToken(), rae.getCompositeId().getName(), pointScaleEntity.getCompositeId().getName())));
        }
        rae.setRuleAwardsBadgesId(Badges);
        rae.setruleAwardsPointScaleId(points);
        rae.setAmountofPoint(Rule.getAwards().getAmountofPoint());

        entity.setAwards(rae);

        List<RulePropertiesEntity> properties = new ArrayList<>();
        int propertyNumber = 1;

        for (ch.heig.gamification.api.model.RuleProperties p : Rule.getProperties()) {
            RulePropertiesEntity propertiesEntity = new RulePropertiesEntity();
            propertiesEntity.setPropertyName(p.getName());
            propertiesEntity.setType(p.getType());
            propertiesEntity.setCompareOperator(p.getCompareOperator());
            propertiesEntity.setValue(p.getValue());
            propertiesEntity.setCompositeId(new CompositeId(Rule.getApiToken(), "ruleProperty" + Rule.getRuleName() + String.valueOf(propertyNumber)));
            propertyNumber++;
            properties.add(propertiesEntity);
        }
        entity.setProperties(properties);
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule Rule = new Rule();
        Rule.setApiToken(entity.getCompositeId().getApiToken());
        Rule.setRuleName(entity.getCompositeId().getName());
        Rule.setEventName(entity.getEventName());


        ch.heig.gamification.api.model.RuleAwards ra = new ch.heig.gamification.api.model.RuleAwards();
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

        List<ch.heig.gamification.api.model.RuleProperties> properties = new ArrayList<>();

        for (RulePropertiesEntity p : entity.getProperties()) {
            ch.heig.gamification.api.model.RuleProperties Properties = new ch.heig.gamification.api.model.RuleProperties();
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
        List<UserEntity> users = userRepository.findByIdApiToken(r.getCompositeId().getApiToken());
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
            userRepository.save(users);
        }
    }

    private void removeEventCount(String eventName, ApplicationEntity app){
        for (RuleEntity r : app.getRules()){
            //if there is still a rule trigger by this event, nothing happen
            if(r.getEventName().equals(eventName)){
                return;
            }
        }
        //if there is no more rule trigger by this event, we delete counters on users
        for(UserEntity u : app.getUsers()){
            for(int i = 0; i < u.getUserGenericEventCountEntities().size(); i++){
                if(u.getUserGenericEventCountEntities().get(i).getLinkTableId().gettable2Id().equals(eventName)){
                    u.getUserGenericEventCountEntities().remove(i);
                    return;
                }
            }
        }
    }
}
