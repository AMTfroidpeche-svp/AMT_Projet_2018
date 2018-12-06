package ch.heig.gamification.api.endpoints;

import io.avalia.gamification.api.RulesApi;
import io.avalia.gamification.api.model.Infos;
import io.avalia.gamification.api.model.Rule;
import ch.heig.gamification.entities.RuleEntity;
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
        String id = newRuleEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Rule>> getRules(Infos infos) {
        List<Rule> Rules = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        ids.add(infos.getApiToken());
        for (RuleEntity RuleEntity : RuleRepository.findAll(ids)) {
            Rules.add(toRule(RuleEntity));
        }
        return ResponseEntity.ok(Rules);
    }


    private RuleEntity toRuleEntity(Rule Rule) {
        RuleEntity entity = new RuleEntity();
        entity.setApiToken(Rule.getApiToken());
        entity.setName(Rule.getName());
        entity.setAwards(Rule.getAwards());
        entity.setProperties(Rule.getProperties());
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule Rule = new Rule();
        Rule.setApiToken(entity.getApiToken());
        Rule.setName(entity.getName());
        Rule.setAwards(entity.getAwards());
        Rule.setProperties(entity.getProperties());
        return Rule;
    }

}
